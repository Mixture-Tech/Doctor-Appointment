import 'package:app/models/disease.dart';
import 'package:app/models/specialization.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../services/ChatbotService.dart';
import '../../styles/colors.dart';
import '../widgets/HeaderWidget.dart';
import 'SpecializationDetailScreen.dart';

class AnswerScreen extends StatefulWidget {
  final String question;
  final Disease disease;
  final Specialization specialization;

  const AnswerScreen({
    Key? key,
    required this.question,
    required this.disease,
    required this.specialization,
  }) : super(key: key);

  @override
  _AnswerScreenState createState() => _AnswerScreenState();
}

class _AnswerScreenState extends State<AnswerScreen> with WidgetsBindingObserver {
  final TextEditingController _controller = TextEditingController();
  final ScrollController _scrollController = ScrollController();
  List<Map<String, String>> messages = [];
  String currentDate = DateFormat('dd/MM/yyyy').format(DateTime.now());

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _loadMessages();
    _addInitialMessages();
  }

  void _addInitialMessages() {
    String currentTime = DateFormat('HH:mm').format(DateTime.now());
    _addMessage('question', widget.question, currentTime);
    String answer = '''Tên bệnh: ${widget.disease.name}\nNguyên nhân: ${widget.disease.causeOfDisease}\nChuyên khoa: ${widget.specialization.name}''';
    _addMessage('answer', answer, currentTime);
  }

  void _addMessage(String type, String text, String time) {
    setState(() {
      messages.add({'type': type, 'text': text, 'time': time});
    });
  }

  Future<void> _saveMessages() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String> savedMessages = messages
        .map((message) => '${message['type']},${message['text']},${message['time']}')
        .toList();
    await prefs.setStringList('chatMessages', savedMessages);
  }

  Future<void> _loadMessages() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String>? savedMessages = prefs.getStringList('chatMessages');
    if (savedMessages != null) {
      setState(() {
        messages = savedMessages.map((message) {
          List<String> parts = message.split(',');
          return {'type': parts[0], 'text': parts[1], 'time': parts[2]};
        }).toList();
      });
    }
  }

  void _navigateToSpecializationDetail() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => SpecializationDetailScreen(
          specializationId: widget.specialization.id,
          specializationName: widget.specialization.name,
        ),
      ),
    );
  }

  Widget _buildMessageBubble(Map<String, String> message) {
    bool isQuestion = message['type'] == 'question';
    String text = message['text']!;
    TextSpan? specializedText;

    RegExp regex = RegExp(r'Chuyên khoa:\s*(.*)');
    final match = regex.firstMatch(text);
    String displayText = text;

    if (match != null) {
      String specialization = match.group(1)!;
      displayText = text.replaceAll(match.group(0)!, 'Chuyên khoa: ');
      specializedText = TextSpan(
        text: specialization,
        style: TextStyle(
          fontSize: 18,
          color: Colors.blue,
          decoration: TextDecoration.underline,
        ),
        recognizer: TapGestureRecognizer()..onTap = _navigateToSpecializationDetail,
      );
    }

    return Padding(
      padding: const EdgeInsets.only(bottom: 20.0),
      child: Align(
        alignment: isQuestion ? Alignment.centerRight : Alignment.centerLeft,
        child: IntrinsicWidth(
          child: Container(
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: isQuestion ? AppColors.white : AppColors.accentBlue,
              borderRadius: BorderRadius.circular(20),
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.shade300,
                  blurRadius: 8,
                  spreadRadius: 2,
                ),
              ],
            ),
            constraints: const BoxConstraints(maxWidth: 300),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                GestureDetector(
                  child: specializedText != null
                      ? Text.rich(
                    TextSpan(
                      text: displayText,
                      children: [specializedText],
                      style: TextStyle(
                        fontSize: 18,
                        color: isQuestion ? AppColors.black : AppColors.white,
                      ),
                    ),
                  )
                      : Text(
                    text,
                    style: TextStyle(
                      fontSize: 18,
                      color: isQuestion ? AppColors.black : AppColors.white,
                    ),
                  ),
                ),
                const SizedBox(height: 10),
                Row(
                  mainAxisAlignment:
                  isQuestion ? MainAxisAlignment.start : MainAxisAlignment.end,
                  children: [
                    Text(
                      message['time']!,
                      style: TextStyle(
                        fontSize: 14,
                        color: isQuestion ? AppColors.black : AppColors.white,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Future<void> _handleSendMessage() async {
    String currentTime = DateFormat('HH:mm').format(DateTime.now());
    String userQuestion = _controller.text;

    _addMessage('question', userQuestion, currentTime);
    _controller.clear();

    try {
      final chatbotService = await ChatbotService.create();
      final newDisease = await chatbotService.predictDiseaseBySymptoms(userQuestion);

      if (newDisease != null) {
        Specialization? specialization =
        await chatbotService.getSpecializationDetails(newDisease.id);
        String answer = '''Tên bệnh: ${newDisease.name}\nNguyên nhân: ${newDisease.causeOfDisease}\nChuyên khoa: ${specialization?.name}''';
        _addMessage('answer', answer, currentTime);
      } else {
        _addMessage('answer', 'Không thể xác định bệnh. Vui lòng cung cấp thêm thông tin.', currentTime);
      }
    } catch (e) {
      _addMessage('answer', 'Đã xảy ra lỗi: $e', currentTime);
    }

    _scrollController.animateTo(
      _scrollController.position.maxScrollExtent,
      duration: Duration(milliseconds: 300),
      curve: Curves.easeOut,
    );

    _saveMessages();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: true,
      body: SafeArea(
        child: Column(
          children: [
            HeaderWidget(
              isHomeScreen: true,
              onIconPressed: () {
                print('Back button pressed');
              },
            ),
            Expanded(
              child: ListView.builder(
                controller: _scrollController,
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                itemCount: messages.length + 1,
                itemBuilder: (context, index) {
                  if (index == 0) {
                    return Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text(
                        currentDate,
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: AppColors.black,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    );
                  }
                  return _buildMessageBubble(messages[index - 1]);
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Row(
                children: [
                  Expanded(
                    child: TextField(
                      controller: _controller,
                      decoration: InputDecoration(
                        hintText: 'Nhập câu hỏi tiếp theo của bạn',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(20),
                          borderSide: BorderSide(
                            color: Colors.grey.shade300,
                          ),
                        ),
                        contentPadding: const EdgeInsets.symmetric(
                          vertical: 10,
                          horizontal: 16,
                        ),
                      ),

                      style: TextStyle(fontSize: 16),
                      onSubmitted: (value) => _handleSendMessage(),
                    ),
                  ),
                  const SizedBox(width: 10),
                  GestureDetector(
                    onTap: _handleSendMessage,
                    child: CircleAvatar(
                      radius: 24,
                      backgroundColor: AppColors.accentBlue,
                      child: Icon(
                        Icons.send,
                        color: Colors.white,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    _controller.dispose();
    _scrollController.dispose();
    super.dispose();
  }
}
