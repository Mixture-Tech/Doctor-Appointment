import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';

class DatePickerTextField extends StatefulWidget {
  final String hintText;
  final IconData prefixIcon;
  final bool isRequired;
  final String? helperText;
  final Function(DateTime)? onDateSelected;

  const DatePickerTextField({
    Key? key,
    required this.hintText,
    required this.prefixIcon,
    this.isRequired = false,
    this.helperText,
    this.onDateSelected,
  }) : super(key: key);

  @override
  _DatePickerTextFieldState createState() => _DatePickerTextFieldState();
}

class _DatePickerTextFieldState extends State<DatePickerTextField> {
  final TextEditingController _controller = TextEditingController();

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(1900),
      lastDate: DateTime.now(),
    );
    if (picked != null) {
      setState(() {
        _controller.text = DateFormat('dd/MM/yyyy').format(picked);
      });
      widget.onDateSelected?.call(picked);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(
          height: 40,
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 18),
            child: TextField(
              controller: _controller,
              readOnly: true,
              onTap: () => _selectDate(context),
              style: AppTextStyles.labelStyle,
              decoration: InputDecoration(
                hintText: null,
                floatingLabelBehavior: FloatingLabelBehavior.never, // Ẩn nhãn khi nhập
                label: RichText(
                  text: TextSpan(
                    text: widget.hintText,
                    style: const TextStyle(
                      fontWeight: FontWeight.w500,
                      color: AppColors.grey,
                      fontSize: 16
                    ),
                    children: widget.isRequired
                        ? [
                      const TextSpan(
                        text: ' *',
                        style: TextStyle(color: AppColors.red), // Dấu * màu đỏ
                      ),
                    ]
                        : [],
                  ),
                ),
                prefixIcon: Icon(widget.prefixIcon, color: AppColors.grey, size: 22),
                filled: true,
                fillColor: AppColors.white,
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
                contentPadding: const EdgeInsets.symmetric(vertical: 0, horizontal: 10),
              ),

            ),
          ),
        ),
        if (widget.helperText != null)
          Padding(
            padding: const EdgeInsets.only(top: 4, left: 18),
            child: Text(
              widget.helperText!,
              style: const TextStyle(color: Colors.grey, fontSize: 12),
            ),
          ),
      ],
    );
  }
}