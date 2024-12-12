import 'package:app/models/disease.dart';
import 'package:app/models/specialization.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../services/ChatbotService.dart';
import '../../styles/colors.dart';
import '../widgets/HeaderWidget.dart';
import 'ChatbotScreen.dart';  // Import ChatbotScreen để điều hướng trở lại
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
  List<Map<String, String>> messages = [];
  ScrollController _scrollController = ScrollController();
  String currentDate = DateFormat('dd/MM/yyyy').format(DateTime.now());

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _loadMessages();
    String currentTime = DateFormat('HH:mm').format(DateTime.now());
    messages.add({
      'type': 'question',
      'text': widget.question,
      'time': currentTime,
    });

    String answer = '''Tên bệnh: ${widget.disease.name}
    Nguyên nhân: ${widget.disease.causeOfDisease}
    Chuyên khoa: ${widget.specialization.name}''';

      messages.add({
      'type': 'answer',
      'text': answer,
      'time': currentTime,
    });
  }

  // Lưu tin nhắn vào SharedPreferences
  Future<void> _saveMessages() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String> savedMessages = messages
        .map((message) => '${message['type']},${message['text']},${message['time']}')
        .toList();
    await prefs.setStringList('chatMessages', savedMessages);
  }

  // Tải tin nhắn đã lưu từ SharedPreferences
  Future<void> _loadMessages() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    List<String>? savedMessages = prefs.getStringList('chatMessages');
    if (savedMessages != null) {
      setState(() {
        messages = savedMessages.map((message) {
          List<String> parts = message.split(',');
          return {
            'type': parts[0],
            'text': parts[1],
            'time': parts[2],
          };
        }).toList();
      });
    }
  }

  // Xử lý khi người dùng nhấn vào chuyên khoa
  void _navigateToSpecializationDetail() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => SpecializationDetailScreen(
          specializationId: widget.specialization.id, // Màn hình chi tiết chuyên khoa
          specializationName: widget.specialization.name,
        ),
      ),
    );
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
                  final message = messages[index - 1];
                  bool isQuestion = message['type'] == 'question';

                  // Kiểm tra nếu câu trả lời chứa từ "Chuyên khoa" và thay thế chúng thành clickable
                  String text = message['text']!;
                  TextSpan? specializedText;

                  // Tìm tên chuyên khoa trong câu trả lời
                  RegExp regex = RegExp(r'Chuyên khoa:\s*(.*)');
                  final match = regex.firstMatch(text);
                  String displayText = text;

                  if (match != null) {
                    // Nếu tìm thấy tên chuyên khoa, tách nó ra
                    String specialization = match.group(1)!; // Thêm (1) thay vì (2)

                    // Thay thế tên chuyên khoa bằng TextSpan có gạch dưới
                    displayText = text.replaceAll(match.group(0)!, 'Chuyên khoa: ');
                    specializedText = TextSpan(
                      text: specialization,
                      style: TextStyle(
                        fontSize: 18,
                        color: Colors.blue,  // Màu sắc tên chuyên khoa
                        decoration: TextDecoration.underline, // Gạch dưới tên chuyên khoa
                        decorationColor: Colors.blue,
                      ),
                      recognizer: TapGestureRecognizer()
                        ..onTap = () => _navigateToSpecializationDetail(),  // Điều hướng khi nhấn vào tên chuyên khoa
                    );
                  }

                  return Padding(
                    padding: const EdgeInsets.only(bottom: 20.0),
                    child: Align(
                      alignment: isQuestion
                          ? Alignment.centerRight
                          : Alignment.centerLeft,
                      child: IntrinsicWidth(
                        child: Container(
                          padding: const EdgeInsets.all(16),
                          decoration: BoxDecoration(
                            color: isQuestion
                                ? AppColors.white
                                : AppColors.accentBlue,
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
                              // Sử dụng Text.rich để kết hợp văn bản và TextSpan
                              GestureDetector(
                                onTap: () {},  // Không làm gì khi nhấn vào phần không phải tên chuyên khoa
                                child: specializedText != null
                                    ? Text.rich(
                                  TextSpan(
                                    text: displayText,  // Văn bản còn lại
                                    children: [specializedText],  // Thêm tên chuyên khoa đã gạch dưới
                                    style: TextStyle(
                                      fontSize: 18,
                                      color: isQuestion
                                          ? AppColors.black
                                          : AppColors.white,
                                    ),
                                  ),
                                )
                                    : Text(
                                  text, // Nếu không có tên chuyên khoa, chỉ hiển thị câu trả lời bình thường
                                  style: TextStyle(
                                    fontSize: 18,
                                    color: isQuestion
                                        ? AppColors.black
                                        : AppColors.white,
                                  ),
                                ),
                              ),
                              const SizedBox(height: 10),
                              Row(
                                mainAxisAlignment: isQuestion
                                    ? MainAxisAlignment.start
                                    : MainAxisAlignment.end,
                                children: [
                                  Text(
                                    message['time']!,
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: isQuestion
                                          ? AppColors.black
                                          : AppColors.white,
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
                    ),
                  ),
                  const SizedBox(width: 10),
                  Container(
                    decoration: BoxDecoration(
                      color: AppColors.accentBlue,
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: IconButton(
                      icon: const Icon(Icons.send),
                      onPressed: () async {
                        String currentTime = DateFormat('HH:mm').format(DateTime.now());
                        String userQuestion = _controller.text;

                        setState(() {
                          messages.add({
                            'type': 'question',
                            'text': userQuestion,
                            'time': currentTime,
                          });
                        });

                        try {
                          // Sử dụng dịch vụ chatbot để dự đoán bệnh mới
                          final chatbotService = await ChatbotService.create();
                          final newDisease = await chatbotService.predictDiseaseBySymptoms(userQuestion);

                          if (newDisease != null) {
                            Specialization? specialization = await chatbotService.getSpecializationDetails(newDisease.id);
                            setState(() {
                              String answer = '''Tên bệnh: ${newDisease.name}
                                Nguyên nhân: ${newDisease.causeOfDisease}
                                Chuyên khoa: ${specialization?.name}''';

                              messages.add({
                                'type': 'answer',
                                'text': answer,
                                'time': currentTime,
                              });
                            });
                          } else {
                            // Xử lý trường hợp không tìm thấy bệnh
                            setState(() {
                              messages.add({
                                'type': 'answer',
                                'text': 'Không thể xác định bệnh. Vui lòng cung cấp thêm thông tin.',
                                'time': currentTime,
                              });
                            });
                          }
                        } catch (e) {
                          // Xử lý lỗi nếu có
                          setState(() {
                            messages.add({
                              'type': 'answer',
                              'text': 'Đã xảy ra lỗi: $e',
                              'time': currentTime,
                            });
                          });
                        }

                        _scrollController.animateTo(
                          _scrollController.position.maxScrollExtent,
                          duration: Duration(milliseconds: 300),
                          curve: Curves.easeOut,
                        );
                        _controller.clear();
                        _saveMessages();
                      },
                      color: Colors.white,
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
}


