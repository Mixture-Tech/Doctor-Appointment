import 'package:app/ui/screens/RegisterScreen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';
import 'package:app/ui/widgets/CustomtextAuthWidget.dart';
import '../widgets/ButtonWidget.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginState();
}

class _LoginState extends State<LoginScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscureText = true;

  void _login() {
    String email = _emailController.text;
    String password = _passwordController.text;

    print("Email: $email, Password: $password");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Stack(
          children: [
            // Background image
            Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage("assets/images/Background_4.png"),
                  fit: BoxFit.cover,
                ),
              ),
            ),
            // Main content
            Center(
              child: SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 24.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      // Logo and Title
                      Column(
                        children: [
                          Image.asset(
                            'assets/images/Logo.png',
                            height: 200,
                          ),
                          const SizedBox(height: 16),
                          const Text(
                            'Đăng nhập',
                            style: TextStyle(
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              color: AppColors.accentBlue,
                            ),
                          ),
                          const SizedBox(height: 40),
                        ],
                      ),
                      // Email field
                      CustomTextField(controller: _emailController,
                          labelText: 'Email',
                          prefixIcon: Icon(Icons.email, color: AppColors.accentBlue,),
                      ),
                      const SizedBox(height: 20),

                      // Password field
                      CustomTextField(
                        controller: _passwordController,
                        labelText: 'Mật khẩu',
                        obscureText: _obscureText, // Truyền giá trị _obscureText
                        prefixIcon: const Icon(Icons.lock, color: AppColors.accentBlue),
                        suffixIcon: IconButton(
                          icon: Icon(
                            _obscureText ? Icons.visibility_off : Icons.visibility,
                            color: AppColors.accentBlue,
                          ),
                          onPressed: () {
                            setState(() {
                              _obscureText = !_obscureText; // Chuyển đổi trạng thái _obscureText
                            });
                          },
                        ),
                      ),
                      const SizedBox(height: 50),

                      // Login button
                     CustomElevatedButton(text: 'Đăng nhập', onPressed: _login),//nút đăng nhập
                      const SizedBox(height: 130),

                      //chuyển trang đăng ký
                      TextButton(
                        onPressed: () {
                          // Hành động khi nhấn "Đăng ký"
                          Navigator.push(
                              context,
                              CupertinoPageRoute(builder: (context) => const Register())
                          );
                        },
                        child: const Text(
                          'Đăng ký',
                          style: TextStyle(
                            color: AppColors.accentBlue,
                          ),
                        ),
                      ),

                      TextButton(
                        onPressed: () {
                          // Hành động khi nhấn "Quên mật khẩu"
                        },
                        child: const Text(
                          'Quên mật khẩu',
                          style: TextStyle(
                              color: AppColors.accentBlue,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
