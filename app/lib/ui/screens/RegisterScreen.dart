import 'package:app/ui/screens/LoginScreen.dart';
import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';
import 'package:app/ui/widgets/ButtonWedget.dart';
import 'package:app/ui/widgets/CustomtextAuth.dart';

class Register extends StatefulWidget {
  const Register({super.key});

  @override
  State<Register> createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  bool _obscureText = true;

  void _register() {
    String username = _usernameController.text;
    String phone = _phoneController.text;
    String email = _emailController.text;
    String password = _passwordController.text;

    print("Username: $username, Phone: $phone, Email: $email, Password: $password");
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
                            'Đăng ký tài khoản',
                            style: TextStyle(
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              color: AppColors.accentBlue,
                            ),
                          ),
                          const SizedBox(height: 40),
                        ],
                      ),
                      //Tên người dùng
                      CustomTextField(controller: _usernameController,
                          labelText: 'Tên người dùng',
                          prefixIcon: Icon(Icons.person, color: AppColors.accentBlue),
                      ),
                      const SizedBox(height: 16),
                      
                      //Phone
                      CustomTextField(controller: _phoneController,
                          labelText: 'Số điện thoại',
                          prefixIcon: Icon(Icons.phone, color: AppColors.accentBlue),
                      ),
                      const SizedBox(height: 16),

                      // Email field
                      CustomTextField(controller: _emailController,
                          labelText: 'Email',
                          prefixIcon: Icon(Icons.email, color: AppColors.accentBlue),
                      ),
                      const SizedBox(height: 16),

                      // Password field
                      CustomTextField(controller: _passwordController,
                          labelText: 'Mật khẩu',
                          obscureText: _obscureText,
                          prefixIcon: Icon(Icons.lock, color: AppColors.accentBlue),
                          suffixIcon: IconButton(
                            icon: Icon(
                                _obscureText ? Icons.visibility_off : Icons.visibility, color: AppColors.accentBlue
                            ),
                            onPressed: () {
                              setState(() {
                                _obscureText = !_obscureText;
                              });
                            },
                          ),
                      ),
                      const SizedBox(height: 24),

                      // Register button
                      CustomElevatedButton(text: 'Đăng ký', onPressed: _register),

                      const SizedBox(height: 55),
                      TextButton(
                        onPressed: () {
                          // Hành động khi nhấn "Đăng nhập"
                          Navigator.push(context,
                            MaterialPageRoute(builder: (context) => const Login()),
                          );
                        },
                        child: const Text(
                          'Đã có tài khoản? Đăng nhập ngay',
                          style: TextStyle(color: AppColors.accentBlue),
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
