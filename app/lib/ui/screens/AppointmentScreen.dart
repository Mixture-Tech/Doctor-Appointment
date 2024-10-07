import 'package:app/ui/widgets/TextFieldWidget.dart';
import 'package:app/ui/widgets/RadioButtonWidget.dart';
import 'package:flutter/material.dart';
import '../../styles/colors.dart';
import '../../styles/text.dart';
import '../widgets/AddressDropdownField.dart';
import '../widgets/ButtonWidget.dart';
import '../widgets/DateTimePickerWidget.dart';
import '../widgets/DoctorInfoWidget.dart';
import '../widgets/HeaderWidget.dart';
import '../widgets/NavigationBarWidget.dart';

class AppointmentScreen extends StatefulWidget {
  const AppointmentScreen({super.key});

  @override
  State<AppointmentScreen> createState() => _AppointmentScreenState();
}

class _AppointmentScreenState extends State<AppointmentScreen> {
  // Thêm các danh sách này ở đầu lớp _AppointmentState
  List<String> provinces = ['Hà Nội', 'TP.HCM', 'Đà Nẵng',]; // Thêm các tỉnh/thành phố khác
  List<String> districts = []; // Sẽ được cập nhật dựa trên tỉnh/thành phố được chọn
  List<String> wards = []; // Sẽ được cập nhật dựa trên quận/huyện được chọn


  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            // Header
            HeaderWidget(
              onBackPressed: () {
                Navigator.pop(context);
              },
            ),
            // Content area with image background
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
                  ListView(
                    children: [
                      Column(
                        children: [
                          const Padding(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                            child: Row(
                              children: [
                                Icon(
                                  Icons.home,
                                  color: AppColors.primaryBlue,
                                ),
                                Text(
                                  " / ",
                                  style: TextStyle(
                                    fontSize: 18,
                                    color: Colors.grey,
                                  ),
                                ),
                                Expanded(
                                  child: Text(
                                    "Đặt lịch khám bệnh",
                                    style: AppTextStyles.subHeaderStyle,
                                    textAlign: TextAlign.left,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Container(
                            height: 1,
                            color: AppColors.primaryBlue,
                            width: double.infinity,
                          ),
                          const SizedBox(height: 10),
                          Column(
                            children: [
                              const Padding(
                                padding: EdgeInsets.symmetric(horizontal: 18),
                                child: DoctorInfoWidget(
                                  name: 'PGS. TS. BS. Nguyễn Xuân Thành',
                                  imageUrl: 'https://example.com/doctor-image.jpg',
                                  date: 'Thứ 3 - 05/09/2024',
                                  time: '09:00 - 09:30',
                                  price: '200.000đ',
                                ),
                              ),
                              // Chọn loại đặt lịch (other_booking, self_booking)
                              const TypeSelectorWidget(),
                              const Padding(
                                padding: EdgeInsets.only(right: 245),
                                child: Text(
                                    'Thông tin người đặt',
                                    style: TextStyle(
                                      color: AppColors.primaryBlue,
                                      fontWeight: FontWeight.bold
                                    ),
                                ),
                              ),
                              const SizedBox(height: 5),
                              const TextFieldWidget(
                                hintText: 'Họ và tên',
                                prefixIcon: Icons.person_outline,
                              ),
                              const SizedBox(height: 10),
                              const TextFieldWidget(
                                  hintText: 'Số điện thoại',
                                  prefixIcon: Icons.phone_outlined,
                              ),
                              const SizedBox(height: 10),
                              const TextFieldWidget(
                                hintText: 'Email',
                                prefixIcon: Icons.email_outlined,
                              ),
                              const SizedBox(height: 20),
                              const Padding(
                                padding: EdgeInsets.only(right: 245),
                                child: Text(
                                  'Thông tin bệnh nhân',
                                  style: TextStyle(
                                      color: AppColors.primaryBlue,
                                      fontWeight: FontWeight.bold
                                  ),
                                ),
                              ),
                              const SizedBox(height: 5),
                              const TextFieldWidget(
                                hintText: 'Họ tên bệnh nhân',
                                prefixIcon: Icons.person_outline,
                                isRequired: true,
                                helperText: 'Hãy ghi rõ họ và tên, viết hoa những chữ cái đầu tiên.\nVí dụ: Nguyễn Văn A',
                              ),
                              // Chọn giới tính (male, female)
                              const Padding(
                                padding: EdgeInsets.symmetric(horizontal: 6),
                                child: GenderSelectorWidget(),
                              ),
                              const TextFieldWidget(
                                hintText: 'Số điện thoại bệnh nhân',
                                prefixIcon: Icons.phone_outlined,
                                isRequired: true
                              ),
                              const SizedBox(height: 10),
                              DatePickerTextField(
                                hintText: 'Ngày/tháng/năm sinh',
                                prefixIcon: Icons.calendar_today_outlined,
                                isRequired: true,
                                onDateSelected: (DateTime selectedDate) {
                                },
                              ),
                              const SizedBox(height: 10),
                              AddressDropdownField(
                                hintText: 'Chọn tỉnh/thành phố',
                                prefixIcon: Icons.location_on_outlined,
                                isRequired: true,
                                items: provinces,
                                onChanged: (value) {
                                  setState(() {
                                    // Cập nhật danh sách quận/huyện dựa trên tỉnh/thành phố được chọn
                                    // Đây chỉ là ví dụ, bạn cần thực hiện logic thực tế để lấy danh sách quận/huyện
                                    districts = ['Quận 1', 'Quận 2', 'Quận 3'];
                                    wards = []; // Reset danh sách phường/xã
                                  });
                                },
                              ),
                              const SizedBox(height: 10),
                              AddressDropdownField(
                                hintText: 'Chọn quận/huyện',
                                prefixIcon: Icons.location_on_outlined,
                                isRequired: true,
                                items: districts,
                                onChanged: (value) {
                                  setState(() {
                                    // Cập nhật danh sách phường/xã dựa trên quận/huyện được chọn
                                    // Đây chỉ là ví dụ, bạn cần thực hiện logic thực tế để lấy danh sách phường/xã
                                    wards = ['Phường 1', 'Phường 2', 'Phường 3'];
                                  });
                                },
                              ),
                              const SizedBox(height: 10),
                              AddressDropdownField(
                                hintText: 'Chọn phường/xã',
                                prefixIcon: Icons.location_on_outlined,
                                isRequired: true,
                                items: wards,
                                onChanged: (value) {
                                  // Xử lý khi phường/xã được chọn
                                },
                              ),
                              const SizedBox(height: 15),
                              const Padding(
                                padding: EdgeInsets.symmetric(horizontal: 18),
                                child: Text(
                                    'Vui lòng điền đầy đủ thông tin, để tiết kiệm thời gian làm thủ tục khám bệnh',
                                    style: AppTextStyles.infoStyle,
                                ),
                              ),
                              const SizedBox(height: 20),
                              CustomElevatedButton(
                                text: 'Đặt lịch ngay',
                                onPressed: () {},
                              ),
                              const SizedBox(height: 100),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                  // Breadcrumb and title
                  const NavigationBarWidget(),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
