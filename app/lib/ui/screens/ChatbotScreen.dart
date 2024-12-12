import 'package:app/services/ChatbotService.dart';
import 'package:app/ui/screens/HomeScreen.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../models/specialization.dart';
import '../../styles/colors.dart';
import '../widgets/HeaderWidget.dart';
import 'AnswerScreen.dart';

class ChatbotScreen extends StatefulWidget {
  const ChatbotScreen({super.key});

  @override
  State<ChatbotScreen> createState() => _ChatbotScreenState();
}

class _ChatbotScreenState extends State<ChatbotScreen> {
  final TextEditingController _controller = TextEditingController();
  late ChatbotService _chatbotService;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _initializeChatbotService();
    _clearSharedPreferences();  // Xóa dữ liệu khi trang được truy cập

  }

  // Phương thức để xóa dữ liệu trong SharedPreferences
  Future<void> _clearSharedPreferences() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('chatMessages');  // Xóa toàn bộ dữ liệu đã lưu
  }

  Future<void> _initializeChatbotService() async {
    _chatbotService = await ChatbotService.create();
  }

  Future<void> _predictDisease(String symptoms) async {
    setState(() {
      _isLoading = true;
    });

    try {
      final disease = await _chatbotService.predictDiseaseBySymptoms(symptoms);


      if (disease != null) {
        Specialization? specialization = await _chatbotService.getSpecializationDetails(disease.id);

        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => AnswerScreen(
              question: symptoms,
              disease: disease,
              specialization: specialization!,
            ),
          ),
        );
      } else {
        // Handle case when no disease is predicted
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Không thể dự đoán bệnh. Vui lòng thử lại.')),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Đã xảy ra lỗi: $e')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
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
                                    ),
                                  ),
                                  _isLoading
                                      ? const CircularProgressIndicator()
                                      : IconButton(
                                    icon: const Icon(Icons.send, color: AppColors.primaryBlue),
                                    onPressed: () {
                                      if (_controller.text.isNotEmpty) {
                                        _predictDisease(_controller.text);
                                      }
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
      "Tôi cảm thấy đau đầu, chóng mặt, mệt mỏi và nôn",
      "Tôi cảm thấy lo âu, nôn, cơ thể mệt mỏi và hay nói lắp",
      "Tôi cảm thấy ngứa, đau đầu và chóng mặt",
      "Tôi bị sốt cao và mệt mỏi kéo dài hơn 3 ngày",
    ];

    List<Widget> questionWidgets = [];

    for (int i = 0; i < questions.length; i++) {
      questionWidgets.add(
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
          child: GestureDetector(
            onTap: () {
              _predictDisease(questions[i]);
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
