import 'package:app/styles/colors.dart';
import 'package:app/ui/screens/ProfileScreen.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:app/ui/widgets/NavigationBarWidget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:app/ui/widgets/AddressDropdownField.dart';

import '../widgets/ButtonWidget.dart';
import '../widgets/DateTimePickerWidget.dart';
import '../widgets/RadioButtonWidget.dart';
import '../widgets/TextFieldWidget.dart';

class PersonalInfoScreen extends StatefulWidget {
  const PersonalInfoScreen({super.key});

  @override
  State<PersonalInfoScreen> createState() => _PersonalInfoScreenState();
}

class _PersonalInfoScreenState extends State<PersonalInfoScreen> {
  List<String> provinces = ['Hà Nội', 'TP.HCM', 'Đà Nẵng',]; // Thêm các tỉnh/thành phố khác
  List<String> districts = []; // Sẽ được cập nhật dựa trên tỉnh/thành phố được chọn
  List<String> wards = []; // Sẽ được cập nhật dựa trên quận/huyện được chọn

  void _submitPersonalInfo() {
    // Xử lý thông tin cá nhân ở đây
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
                  CupertinoPageRoute(builder: (context) => ProfileScreen()), // Chuyển đến trang đăng nhập
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
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center, // Căn giữa các widget
                      children: [
                        SizedBox(height: 20),

                        // Hình đại diện
                        CircleAvatar(
                          radius: 50,
                          backgroundImage: AssetImage("assets/images/Personal_Avatar.png"),
                        ),
                        const SizedBox(height: 20),
                        Column(
                        crossAxisAlignment: CrossAxisAlignment.start, // Căn lề trái tất cả các mục
                        children: [
                        // Họ và tên
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                          child: Text(
                            'Họ và tên',
                            style: TextStyle(
                              color: AppColors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        TextFieldWidget(
                          hintText: 'Họ và tên',
                          prefixIcon: Icons.person_outline,
                          isRequired: true,
                        ),
                        const SizedBox(height: 15),

                        // Ngày/tháng/năm sinh
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                          child: Text(
                            'Ngày/tháng/năm sinh',
                            style: TextStyle(
                              color: AppColors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        DatePickerTextField(
                          hintText: 'Ngày/tháng/năm sinh',
                          prefixIcon: Icons.calendar_today_outlined,
                          isRequired: true,
                          onDateSelected: (DateTime selectedDate) {
                            // Handle date selection
                          },
                        ),
                        const SizedBox(height: 10),


                          Padding(
                            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                            child: Text(
                              'Giới tính',
                              style: TextStyle(
                                color: AppColors.black,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                        // Giới tính
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 10),
                          // child: GenderSelectorWidget(),
                        ),

                        // Tỉnh
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                          child: Text(
                            'Tỉnh/Thành phố',
                            style: TextStyle(
                              color: AppColors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        AddressDropdownField(
                          hintText: 'Chọn tỉnh/thành phố',
                          prefixIcon: Icons.location_on_outlined,
                          // isRequired: true,
                          // items: provinces,
                          onChanged: (value) {
                            setState(() {
                              // districts = ['Quận 1', 'Quận 2', 'Quận 3'];
                              // wards = [];
                            });
                          }, fetchItems: () { return Future.value(provinces.map((e) => {'name': e}).toList()); },
                        ),

                        // Huyện
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                          child: Text(
                            'Quận/Huyện',
                            style: TextStyle(
                              color: AppColors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        AddressDropdownField(
                          hintText: 'Chọn quận/huyện',
                          prefixIcon: Icons.location_on_outlined,
                          // isRequired: true,
                          // items: districts,
                          onChanged: (value) {
                            setState(() {
                              // wards = ['Phường 1', 'Phường 2', 'Phường 3'];
                            });
                          }, fetchItems: () { return Future.value(districts.map((e) => {'name': e}).toList()); },
                        ),

                        // Địa chỉ
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
                          child: Text(
                            'Phường/Xã',
                            style: TextStyle(
                              color: AppColors.black,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        AddressDropdownField(
                          hintText: 'Phường/xã',
                          prefixIcon: Icons.location_on_outlined,
                          // isRequired: true,
                          // items: wards,
                          onChanged: (value) {
                            // Handle ward selection
                          }, fetchItems: () {  return Future.value(wards.map((e) => {'name': e}).toList()); },
                        ),
                        ],
                        ),
                        const SizedBox(height: 20),

                        // Nút Lưu và Xem hồ sơ
                        CustomElevatedButton(text: 'Lưu', onPressed: _submitPersonalInfo),
                        // Row(
                        //   mainAxisAlignment: MainAxisAlignment.center,
                        //   children: [
                        //     CustomElevatedButton(text: 'Lưu', onPressed: _submitPersonalInfo),
                        //     const SizedBox(width: 40),
                        //     CustomElevatedButton(text: 'Xem hồ sơ', onPressed: _submitPersonalInfo, backgroundColor: AppColors.secondaryYellow),
                        //   ],
                        // ),
                      ],

                    ),
                  ),
                  // NavigationBarWidget(),  // Navigation bar phía dưới
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
