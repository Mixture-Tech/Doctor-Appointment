import 'package:app/styles/text.dart';
import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';

class CustomElevatedButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final Color backgroundColor; // Tham số màu nền tùy chỉnh

  const CustomElevatedButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.backgroundColor = AppColors.accentBlue, // Giá trị mặc định
  });

  @override
  Widget build(BuildContext context) {
    final ButtonStyle style = ElevatedButton.styleFrom(
      textStyle: AppTextStyles.buttonStyle,
      backgroundColor: backgroundColor, // Sử dụng màu nền tùy chỉnh
      foregroundColor: Colors.white, // Hoặc AppColors.white nếu bạn đã định nghĩa
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 15), // Padding
    );

    return SizedBox(
      width: 160, // Chiều rộng cố định
      height: 50, // Chiều cao cố định
      child: ElevatedButton(
        style: style,
        onPressed: onPressed,
        child: Text(text),
      ),
    );
  }
}
