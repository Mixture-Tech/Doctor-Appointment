import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';
import 'package:flutter/material.dart';

class TextFieldWidget extends StatelessWidget {
  final String hintText;
  final IconData prefixIcon;
  final bool isRequired;
  final String? helperText;
  final TextEditingController? controller;

  const TextFieldWidget({
    super.key,
    required this.hintText,
    required this.prefixIcon,
    this.isRequired = false,
    this.helperText,
    this.controller,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(
          height: 40, // Đặt chiều cao cố định cho TextField
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 18),
            child: TextField(
              controller: controller,
              style: const TextStyle(color: Colors.grey), // Đặt màu chữ là xám
              decoration: InputDecoration(
                hintText: null, // Không sử dụng hintText gốc
                label: RichText(
                  text: TextSpan(
                    text: hintText, // Nội dung hintText
                    style: const TextStyle(
                        fontWeight: FontWeight.w500,
                        color: AppColors.grey,
                        fontSize: 16
                    ),
                    children: isRequired
                        ? [
                      const TextSpan(
                        text: ' *', // Dấu * màu đỏ
                        style: TextStyle(color: AppColors.red),
                      ),
                    ]
                        : [],
                  ),
                ),
                prefixIcon: Icon(prefixIcon, color: AppColors.grey, size: 22), // Giảm kích thước icon
                filled: true,
                fillColor: AppColors.white, // Đặt màu nền là trắng
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: AppColors.grey),
                ),
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: AppColors.grey),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: AppColors.primaryBlue),
                ),
                contentPadding: const EdgeInsets.symmetric(vertical: 0, horizontal: 10), // Giảm padding bên trong
              ),
            ),
          ),
        ),
        if (helperText != null)
          Padding(
            padding: const EdgeInsets.only(top: 4, left: 18),
            child: Text(
              helperText!,
              style: const TextStyle(color: Colors.grey, fontSize: 12),
            ),
          ),
      ],
    );
  }
}
