import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';  // Import thư viện SharedPreferences
import '../../styles/colors.dart';
import '../widgets/HeaderWidget.dart';
import 'ChatbotScreen.dart';  // Import ChatbotScreen để điều hướng trở lại

class AnswerScreen extends StatefulWidget {
  final String question;
  final String answer;

  AnswerScreen({required this.question, required this.answer});

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
    WidgetsBinding.instance.addObserver(this);  // Đăng ký observer
    _loadMessages();  // Load saved messages if available
    String currentTime = DateFormat('HH:mm').format(DateTime.now());
    messages.add({
      'type': 'question',
      'text': widget.question,
      'time': currentTime,
    });
    messages.add({
      'type': 'answer',
      'text': widget.answer,
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

  // Xóa tin nhắn khi người dùng thoát app
  Future<void> _clearMessages() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove('chatMessages'); // Xóa dữ liệu đã lưu
  }

  // Thêm phương thức để xóa dữ liệu khi người dùng quay lại trang ChatbotScreen
  void _clearDataAndNavigate() async {
    await _clearMessages(); // Xóa dữ liệu đã lưu
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => ChatbotScreen()), // Chuyển hướng về ChatbotScreen
    );
  }

  @override
  void dispose() {
    _clearMessages();
    WidgetsBinding.instance.removeObserver(this);  // Gỡ bỏ observer khi dispose
    super.dispose();
  }


  // Kiểm tra vòng đời ứng dụng và xóa tin nhắn khi ra nền
  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    super.didChangeAppLifecycleState(state);

    if (state == AppLifecycleState.paused || state == AppLifecycleState.detached) {
      _clearMessages();  // Xóa tin nhắn khi ứng dụng bị tạm dừng hoặc bị đóng
    }
  }

  void _sendMessage() {
    final String newQuestion = _controller.text;
    if (newQuestion.isNotEmpty) {
      String currentTime = DateFormat('HH:mm').format(DateTime.now());
      setState(() {
        messages.add({
          'type': 'question',
          'text': newQuestion,
          'time': currentTime,
        });
        messages.add({
          'type': 'answer',
          'text': 'Câu trả lời cho câu hỏi: "$newQuestion"',
          'time': currentTime,
        });
      });
      _scrollController.animateTo(
        _scrollController.position.maxScrollExtent,
        duration: Duration(milliseconds: 300),
        curve: Curves.easeOut,
      );
      _controller.clear();
      _saveMessages();  // Lưu tin nhắn mới
    }
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
              onIconPressed: _clearDataAndNavigate,  // Xóa dữ liệu khi nhấn vào nút quay lại
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
                              Text(
                                message['text']!,
                                style: TextStyle(
                                  fontSize: 18,
                                  color: isQuestion
                                      ? AppColors.black
                                      : AppColors.white,
                                ),
                                softWrap: true,
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
                      onPressed: _sendMessage,
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
