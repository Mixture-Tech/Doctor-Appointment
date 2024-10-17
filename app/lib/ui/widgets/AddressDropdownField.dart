import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';

class AddressDropdownField extends StatefulWidget {
  final String hintText;
  final IconData prefixIcon;
  final bool isRequired;
  // final List<String> items;
  // final Function(String?)? onChanged;

  final Future<List<Map<String, dynamic>>> Function() fetchItems;
  final Function(Map<String, dynamic>?) onChanged;

  const AddressDropdownField({
    Key? key,
    required this.hintText,
    required this.prefixIcon,
    this.isRequired = false,
    required this.fetchItems,
    required this.onChanged,
  }) : super(key: key);

  @override
  _AddressDropdownFieldState createState() => _AddressDropdownFieldState();
}

class _AddressDropdownFieldState extends State<AddressDropdownField> {
  Map<String, dynamic>? _selectedValue;
  List<Map<String, dynamic>> _items = [];
  bool _isLoading = false;

  @override
  void initState(){
    super.initState();
    _loadItems();
  }

  Future<void> _loadItems() async{
    setState(() {
      _isLoading = true;
    });
    try{
      final items = await widget.fetchItems();
      setState(() {
        _items = items;
        _isLoading = false;
      });
    }catch(e){
      setState(() {
        _isLoading = false;
      });
    }
  }

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
          child: DropdownButton<Map<String, dynamic>>(
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
              return _items.map<Widget>((Map<String, dynamic> item) {
                return Row(
                  children: [
                    Icon(widget.prefixIcon, color: AppColors.grey, size: 22),
                    const SizedBox(width: 8),
                    Text(item['ProvinceName'] ?? item['DistrictName'] ?? item['WardName'] ?? ''),
                  ],
                );
              }).toList();
            },
            onChanged: (Map<String, dynamic>? newValue) {
              setState(() {
                _selectedValue = newValue;
              });
                widget.onChanged(newValue);
            },
            items: _items.map<DropdownMenuItem<Map<String, dynamic>>>((Map<String, dynamic> value) {
              return DropdownMenuItem<Map<String, dynamic>>(
                value: value,
                child: Text(value['ProvinceName'] ?? value['DistrictName'] ?? value['WardName'] ?? ''),
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