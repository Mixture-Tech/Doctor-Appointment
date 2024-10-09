import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';
import 'package:flutter/material.dart';

// Enum cho other_booking và self_booking
enum SingingCharacter { other_booking, self_booking }

// Enum cho male và female
enum Gender { male, female }

// TypeSelectorWidget cho other_booking và self_booking
class TypeSelectorWidget extends StatelessWidget {
  final SingingCharacter character;
  final ValueChanged<SingingCharacter?> onChanged;

  const TypeSelectorWidget({
    super.key,
    required this.character,
    required this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Radio<SingingCharacter>(
          value: SingingCharacter.self_booking,
          groupValue: character,
          activeColor: AppColors.primaryBlue,
          fillColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
            return states.contains(MaterialState.selected)
                ? AppColors.primaryBlue
                : AppColors.grey;
          }),
          onChanged: onChanged,
        ),
        const Text('Đặt cho bản thân'),
        const SizedBox(width: 20),
        Radio<SingingCharacter>(
          value: SingingCharacter.other_booking,
          groupValue: character,
          activeColor: AppColors.primaryBlue,
          fillColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
            return states.contains(MaterialState.selected)
                ? AppColors.primaryBlue
                : AppColors.grey;
          }),
          onChanged: onChanged,
        ),
        const Text('Đặt cho người thân'),
      ],
    );
  }
}
// GenderSelectorWidget cho lựa chọn giới tính
class GenderSelectorWidget extends StatefulWidget {
  const GenderSelectorWidget({super.key});

  @override
  _GenderSelectorWidgetState createState() => _GenderSelectorWidgetState();
}

class _GenderSelectorWidgetState extends State<GenderSelectorWidget> {
  Gender _gender = Gender.male;

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: <Widget>[
        Radio<Gender>(
          value: Gender.male,
          groupValue: _gender,
          activeColor: AppColors.grey,
          fillColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
            return states.contains(MaterialState.selected)
                ? AppColors.grey // Màu khi được chọn
                : AppColors.grey; // Màu viền xám khi không được chọn
          }),
          onChanged: (Gender? value) {
            setState(() {
              _gender = value!;
            });
          },
        ),
        const Text(
          'Nam',
          style: TextStyle(
            fontSize: 14,
            color: AppColors.grey
          ),
        ),
        Radio<Gender>(
          value: Gender.female,
          groupValue: _gender,
          activeColor: AppColors.grey,
          fillColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
            return states.contains(MaterialState.selected)
                ? AppColors.grey
                : AppColors.grey;
          }),
          onChanged: (Gender? value) {
            setState(() {
              _gender = value!;
            });
          },
        ),
        const Text(
          'Nữ',
          style: TextStyle(
            fontSize: 14,
            color: AppColors.grey
          ),
        ),
      ],
    );
  }
}
