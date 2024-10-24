import 'package:app/styles/colors.dart';
import 'package:app/ui/widgets/ButtonWidget.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:app/ui/screens/ProfileScreen.dart';
import 'package:app/ui/widgets/TextFieldWidget.dart';
import 'package:app/styles/text.dart';
import 'package:app/ui/screens/LoginScreen.dart';

class ForgotPasswordScreen extends StatelessWidget {
  const ForgotPasswordScreen({super.key});

  void _submitForgotPassword() {
    // Xử lý logic quên mật khẩu ở đây
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            // Header
            HeaderWidget(
              isHomeScreen: false,
              onIconPressed: () {
                Navigator.pop(
                  context,
                  CupertinoPageRoute(builder: (context) => const LoginScreen()), // Chuyển đến trang Profile
                );
              },
            ),
            Expanded(
              child: Stack(
                fit: StackFit.expand,
                children: [
                  // Background image
                  Container(
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage("assets/images/Background_3.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                  SingleChildScrollView(
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          const SizedBox(height: 50),

                          // Tiêu đề
                          Text(
                            'Quên mật khẩu',
                            style: AppTextStyles.labelStyle.copyWith(
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              color: AppColors.black,
                            ),
                          ),
                          const SizedBox(height: 30),
                          const Padding(
                            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                            child: Text(
                              'Để khôi phục mật khẩu, vui lòng nhập email đã đăng ký hoặc đăng nhập tài khoản.',
                              style: TextStyle(
                                color: AppColors.black,
                                fontSize: 16,
                              ),
                              textAlign: TextAlign.center,
                            ),
                          ),
                          const SizedBox(height: 10),
                          TextFieldWidget(
                            hintText: 'Nhập email',
                            prefixIcon: Icons.mail,
                            isRequired: true,
                          ),

                          const SizedBox(height: 30),

                          // Nút Gửi
                          CustomElevatedButton(
                            text: 'Gửi',
                            onPressed: _submitForgotPassword,

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
}
