import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../styles/colors.dart';
import '../widgets/HeaderWidget.dart';
import 'AnswerScreen.dart';

class ChatbotScreen extends StatefulWidget {
  const ChatbotScreen({super.key});

  @override
  State<ChatbotScreen> createState() => _ChatbotScreenState();
}

class _ChatbotScreenState extends State<ChatbotScreen> {
  TextEditingController _controller = TextEditingController();
  String _userQuestion = '';
  String _aiAnswer = '';

  @override
  void initState() {
    super.initState();
    _clearSharedPreferences();  // Xóa dữ liệu khi trang được truy cập
  }

  // Phương thức để xóa dữ liệu trong SharedPreferences
  Future<void> _clearSharedPreferences() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();  // Xóa toàn bộ dữ liệu đã lưu
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: [
            HeaderWidget(
              isHomeScreen: true,
              onIconPressed: () {
                print('Back button pressed');
              },
            ),
            Expanded(
              child: Stack(
                children: [
                  DecoratedBox(
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage("assets/images/Background_3.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                    child: SingleChildScrollView(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const SizedBox(height: 15),
                          const Center(
                            child: Padding(
                              padding: EdgeInsets.symmetric(horizontal: 16.0),
                              child: Text(
                                'Nơi khởi nguồn sức khỏe',
                                style: TextStyle(
                                  fontSize: 20,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.green,
                                ),
                              ),
                            ),
                          ),
                          const SizedBox(height: 16),
                          Padding(
                            padding: const EdgeInsets.symmetric(horizontal: 16.0),
                            child: Container(
                              padding: const EdgeInsets.all(12),
                              decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius: BorderRadius.circular(8),
                                boxShadow: [
                                  BoxShadow(
                                    color: Colors.black.withOpacity(0.3),
                                    blurRadius: 5,
                                  ),
                                ],
                              ),
                              child: Row(
                                children: [
                                  Expanded(
                                    child: TextField(
                                      controller: _controller,
                                      decoration: const InputDecoration(
                                        hintText: "Đặt câu hỏi với Trợ lý AI",
                                        border: InputBorder.none,
                                        hintStyle: TextStyle(color: Colors.black54),
                                      ),
                                      style: const TextStyle(color: Colors.black),
                                      onChanged: (value) {
                                        // Xử lý logic khi người dùng nhập
                                        print("Người dùng nhập: $value");
                                      },
                                    ),
                                  ),
                                  IconButton(
                                    icon: const Icon(Icons.send, color: AppColors.primaryBlue),
                                    onPressed: () {
                                      setState(() {
                                        // Cập nhật _userQuestion trước khi tạo câu trả lời
                                        _userQuestion = _controller.text;
                                        _aiAnswer = 'Câu trả lời của AI cho câu hỏi: "$_userQuestion"'; // Sử dụng _userQuestion sau khi đã cập nhật
                                      });

                                      // Chuyển màn hình sau khi gửi câu hỏi
                                      Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => AnswerScreen(
                                            question: _userQuestion, // Truyền câu hỏi
                                            answer: _aiAnswer, // Truyền câu trả lời
                                          ),
                                        ),
                                      );
                                    },
                                  ),
                                ],
                              ),
                            ),
                          ),
                          const SizedBox(height: 16),
                          ..._buildQuestionList(),
                          const SizedBox(height: 16),
                          const Padding(
                            padding: EdgeInsets.symmetric(horizontal: 16.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Text(
                                  'Cuộc trò chuyện của bạn được hỗ trợ bởi AI, nếu có thông tin không muốn chia sẻ, bạn vui lòng không đề cập trong cuộc trò chuyện.',
                                  style: TextStyle(color: Colors.black54, fontSize: 15),
                                  textAlign: TextAlign.left,
                                ),
                                Text(
                                  'Không nên tự ý áp dụng kiến thức y khoa, cần tham khảo ý kiến của bác sĩ.',
                                  style: TextStyle(color: Colors.black54, fontSize: 15),
                                  textAlign: TextAlign.left,
                                )
                              ],
                            ),
                          ),
                        ],
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

  List<Widget> _buildQuestionList() {
    final List<String> questions = [
      'Bệnh nhân bị u sàn miệng...',
      'Lịch sử hình thành Bệnh viện Đại học Y Hà Nội',
      'Số điện thoại của Bệnh viện Nhân dân 115',
      'Cách đặt khám tại khoa Thận Tiết niệu...',
      'Bác sĩ khoa Xương và Điều trị ngoại trú...',
      'Địa chỉ của Bệnh viện Đại học Y Dược...',
    ];

    List<Widget> questionWidgets = [];

    for (int i = 0; i < questions.length; i++) {
      questionWidgets.add(
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
          child: GestureDetector(
            onTap: () {
              // Khi nhấn vào câu hỏi, chuyển đến trang AnswerScreen với câu hỏi đã chọn
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => AnswerScreen(
                    question: questions[i], // Truyền câu hỏi đã chọn
                    answer: 'Câu trả lời của AI cho câu hỏi: "${questions[i]}"', // Giả sử bạn có câu trả lời AI
                  ),
                ),
              );
            },
            child: Row(
              children: [
                Expanded(
                  child: Text(
                    questions[i],
                    style: const TextStyle(color: AppColors.primaryBlue, fontSize: 15),
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
                const Icon(Icons.add, color: Colors.blue),
              ],
            ),
          ),
        ),
      );

      // Thêm Divider trừ dòng cuối cùng
      if (i < questions.length - 1) {
        questionWidgets.add(
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Container(
              height: 1, // Độ dày của thanh ngang
              color: AppColors.accentBlue, // Màu thanh ngang
            ),
          ),
        );
      }
    }

    return questionWidgets;
  }
}
