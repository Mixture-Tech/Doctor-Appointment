import 'package:app/services/ProviceGHNApiService.dart';
import 'package:app/ui/screens/Appointment.dart';
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

class AppointmentDetailScreen extends StatefulWidget {
  const AppointmentDetailScreen({super.key});

  @override
  State<AppointmentDetailScreen> createState() => _AppointmentDetailScreenState();
}

class _AppointmentDetailScreenState extends State<AppointmentDetailScreen> {
  // List<String> provinces = ['Hà Nội', 'TP.HCM', 'Đà Nẵng',]; // Thêm các tỉnh/thành phố khác
  // List<String> districts = []; // Sẽ được cập nhật dựa trên tỉnh/thành phố được chọn
  // List<String> wards = []; // Sẽ được cập nhật dựa trên quận/huyện được chọn

  Map<String, dynamic>? _selectedProvince;
  Map<String, dynamic>? _selectedDistrict;
  Map<String, dynamic>? _selectedWard;
  
  SingingCharacter _bookingType = SingingCharacter.self_booking;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            // Header
            HeaderWidget(
              onBackPressed: () {
                Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => const AppointmentScreen())
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
                              TypeSelectorWidget(
                                character: _bookingType,
                                onChanged: (SingingCharacter? value) {
                                  setState(() {
                                    _bookingType = value!;
                                  });
                                },
                              ),

                              if (_bookingType == SingingCharacter.other_booking) ...[
                                _buildBookerInfo(),
                              ],
                              _buildPatientInfo(),
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

  Widget _buildBookerInfo(){
    return const Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.only(left: 18, bottom: 5),
          child: Text(
            'Thông tin người đặt',
            style: TextStyle(
                color: AppColors.primaryBlue,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
        TextFieldWidget(
          hintText: 'Họ và tên',
          prefixIcon: Icons.person_outline,
        ),
        SizedBox(height: 10),
        TextFieldWidget(
          hintText: 'Số điện thoại',
          prefixIcon: Icons.phone_outlined,
        ),
        SizedBox(height: 10),
        TextFieldWidget(
          hintText: 'Email',
          prefixIcon: Icons.email_outlined,
        ),
        SizedBox(height: 20),
      ],
    );
  }

  Widget _buildPatientInfo() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.only(left: 18, bottom: 5),
          child: Text(
            _bookingType == SingingCharacter.self_booking ? 'Thông tin bệnh nhân' : 'Thông tin người thân',
            style: const TextStyle(
                color: AppColors.primaryBlue,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
        const TextFieldWidget(
          hintText: 'Họ tên bệnh nhân',
          prefixIcon: Icons.person_outline,
          isRequired: true,
          helperText: 'Hãy ghi rõ họ và tên, viết hoa những chữ cái đầu tiên.\nVí dụ: Nguyễn Văn A',
        ),
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
            // Handle date selection
          },
        ),
        const SizedBox(height: 10),
        AddressDropdownField(
          // key: ValueKey('province-${_selectedProvince?.toString()??''}'),
          hintText: 'Chọn tỉnh/thành phố',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          fetchItems: ProvinceGHNApiService.getProvinces,
          onChanged: (value) {
            setState(() {
              _selectedProvince = value;
              _selectedDistrict = null;
              _selectedWard = null;
            });
          },
        ),
        const SizedBox(height: 10),
        // if(_selectedProvince != null)
        AddressDropdownField(
          key: ValueKey('district-${_selectedProvince?.toString()??''}'),
          hintText: 'Chọn quận/huyện',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          fetchItems: () => _selectedProvince != null ? ProvinceGHNApiService.getDistricts(_selectedProvince!['ProvinceID']) : Future.value([]),
          onChanged: (value) {
            setState(() {
              _selectedDistrict = value;
              _selectedWard = null;
            });
          },
        ),
        const SizedBox(height: 10),
        // if(_selectedDistrict != null)
        AddressDropdownField(
          key: ValueKey('ward-${_selectedDistrict?.toString()??''}'),
          hintText: 'Chọn phường/xã',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          fetchItems: () => _selectedDistrict != null ? ProvinceGHNApiService.getWards(_selectedDistrict!['DistrictID']) : Future.value([]),
          onChanged: (value) {
            _selectedWard = value;
          },
        ),
      ],
    );
  }
}


