import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';

class AddressDropdownField extends StatefulWidget {
  final String hintText;
  final IconData prefixIcon;
  final bool isRequired;
  final List<String> items;
  final Function(String?)? onChanged;

  const AddressDropdownField({
    Key? key,
    required this.hintText,
    required this.prefixIcon,
    this.isRequired = false,
    required this.items,
    this.onChanged,
  }) : super(key: key);

  @override
  _AddressDropdownFieldState createState() => _AddressDropdownFieldState();
}

class _AddressDropdownFieldState extends State<AddressDropdownField> {
  String? _selectedValue;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 18),
      child: Container(
        height: 40,
        decoration: BoxDecoration(
          color: AppColors.white,
          borderRadius: BorderRadius.circular(8),
          border: Border.all(color: AppColors.grey),
        ),
        child: DropdownButtonHideUnderline(
          child: DropdownButton<String>(
            value: _selectedValue,
            icon: const Icon(Icons.arrow_drop_down, color: AppColors.grey),
            hint: Row(
              children: [
                Icon(widget.prefixIcon, color: AppColors.grey, size: 22),
                const SizedBox(width: 8),
                Text(
                  widget.hintText,
                  style: AppTextStyles.labelStyle,
                ),
                if (widget.isRequired)
                  const Text(' *', style: TextStyle(color: AppColors.red)),
              ],
            ),
            selectedItemBuilder: (BuildContext context) {
              return widget.items.map<Widget>((String item) {
                return Row(
                  children: [
                    Icon(widget.prefixIcon, color: AppColors.grey, size: 22),
                    const SizedBox(width: 8),
                    Text(item, style: AppTextStyles.labelStyle),
                  ],
                );
              }).toList();
            },
            onChanged: (String? newValue) {
              setState(() {
                _selectedValue = newValue;
              });
              widget.onChanged?.call(newValue);
            },
            items: widget.items.map<DropdownMenuItem<String>>((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(value),
              );
            }).toList(),
            isExpanded: true,
            dropdownColor: AppColors.white,
            padding: const EdgeInsets.symmetric(horizontal: 15),
          ),
        ),
      ),
    );
  }
}