import 'package:app/styles/colors.dart';
import 'package:app/ui/screens/AppointmentDetailScreen.dart';
import 'package:app/ui/screens/AppointmentScreen.dart';
import 'package:app/ui/screens/SpecializationScreen.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../widgets/NavigationBarWidget.dart';

class SpecializationDetailScreen extends StatefulWidget {
  const SpecializationDetailScreen({super.key});

  @override
  State<SpecializationDetailScreen> createState() => _SpecializationDetailScreenState();
}

class _SpecializationDetailScreenState extends State<SpecializationDetailScreen> {
  String selectedLocation = 'Toàn quốc';
  String selectedDate = '17/10/2024';
  String? selectedTimeSlot; // Thời gian khám đã chọn
  final List<String> locations = ['Toàn quốc', 'Hà Nội', 'TP. Hồ Chí Minh', 'Đà Nẵng', 'Cần Thơ'];
  final List<String> dateOptions = ['17/10/2024', '18/10/2024', '19/10/2024', '20/10/2024'];
  final List<String> timeSlots = ['15:30 - 16:00', '16:00 - 16:30', '16:30 - 17:00', '17:00 - 17:30']; // Thời gian khám

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            SizedBox(
              height: 65,
              child: HeaderWidget(
                isHomeScreen: false,
                onIconPressed: () {
                  Navigator.of(context).pop(
                    CupertinoPageRoute(builder: (context) => const SpecializationScreen()),
                  );
                },
              ),
            ),
            Expanded(
              child: SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Cơ Xương Khớp',
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(
                        height: 8,
                      ),
                      const Text(
                        'Danh sách các bác sĩ uy tín đầu ngành Cơ Xương Khớp tại Việt Nam:',
                        style: TextStyle(fontSize: 14),
                      ),
                      const SizedBox(
                        height: 8,
                      ),
                      const Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('• Các chuyên gia có quá trình đào tạo bài bản, nhiều kinh nghiệm'),
                          Text('• Các giáo sư, phó giáo sư đang trực tiếp nghiên cứu và giảng dạy tại Đại học Y khoa Hà Nội'),
                          Text('• Các bác sĩ đã, đang công tác tại các bệnh viện hàng đầu'),
                        ],
                      ),
                      GestureDetector(
                        onTap: () {},
                        child: const Text(
                          'Xem thêm',
                          style: TextStyle(
                            color: AppColors.primaryBlue,
                            fontSize: 14,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 16,
                      ),
                      DropdownButton<String>(
                        value: selectedLocation,
                        icon: const Icon(Icons.arrow_drop_down),
                        elevation: 6,
                        style: const TextStyle(
                          color: Colors.black,
                          fontSize: 16,
                        ),
                        underline: Container(
                          height: 2,
                          color: Colors.blue,
                        ),
                        onChanged: (String? newValue) {
                          setState(() {
                            selectedLocation = newValue!;
                          });
                        },
                        items: locations.map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                      ),
                      Container(
                        padding: const EdgeInsets.all(16.0),
                        margin: const EdgeInsets.only(top: 8.0),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(8),
                          boxShadow: [
                            BoxShadow(
                              color: Colors.grey.withOpacity(0.2),
                              spreadRadius: 1,
                              blurRadius: 5,
                              offset: const Offset(0, 3),
                            ),
                          ],
                        ),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Row(
                              children: [
                                CircleAvatar(
                                  radius: 40,
                                  backgroundImage: AssetImage('assets/images/bs1.jpg'),
                                ),
                                SizedBox(
                                  width: 16,
                                ),
                                Expanded(
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        'Tiến sĩ, Bác sĩ chuyên khoa II Lê Quốc Việt',
                                        style: TextStyle(
                                          fontSize: 16,
                                          fontWeight: FontWeight.bold,
                                          color: AppColors.primaryBlue,
                                        ),
                                      ),
                                      SizedBox(
                                        height: 4,
                                      ),
                                      Text(
                                        'Hơn 30 năm kinh nghiệm khám và điều trị các bệnh nội cơ xương khớp và 40 năm kinh nghiệm khám Nội tổng quát',
                                        style: TextStyle(
                                          fontSize: 14,
                                        ),
                                      ),
                                      SizedBox(
                                        height: 4,
                                      ),
                                      Text(
                                        'Nguyên Phó Giám đốc Bệnh viện E',
                                        style: TextStyle(
                                          fontSize: 14,
                                          color: Colors.grey,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(
                              height: 12,
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                DropdownButton<String>(
                                  value: selectedDate,
                                  icon: const Icon(Icons.arrow_drop_down),
                                  elevation: 16,
                                  style: const TextStyle(
                                    color: AppColors.primaryBlue,
                                    fontWeight: FontWeight.bold,
                                    fontSize: 16,
                                  ),
                                  underline: Container(
                                    height: 2,
                                    color: AppColors.primaryBlue,
                                  ),
                                  onChanged: (String? newValue) {
                                    setState(() {
                                      selectedDate = newValue!;
                                    });
                                  },
                                  items: dateOptions.map<DropdownMenuItem<String>>((String value) {
                                    return DropdownMenuItem<String>(
                                      value: value,
                                      child: Text(value),
                                    );
                                  }).toList(),
                                ),
                              ],
                            ),
                            const Divider(),
                            const Row(
                              children: [
                                Icon(
                                  Icons.calendar_month,
                                ),
                                Text(
                                  'LỊCH KHÁM',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ],
                            ),
                            SingleChildScrollView(
                              scrollDirection: Axis.horizontal,
                              child: Row(
                                children: timeSlots.map((time) {
                                  return Padding(
                                    padding: const EdgeInsets.only(right: 8.0),
                                    child: OutlinedButton(
                                      onPressed: () {
                                        setState(() {
                                          selectedTimeSlot = time;
                                        });
                                        Navigator.push(
                                            context,
                                            CupertinoPageRoute(builder: (context) => const AppointmentDetailScreen(
                                                previousScreen: SpecializationDetailScreen()
                                              )
                                            )
                                        );
                                      },
                                      style: OutlinedButton.styleFrom(
                                        backgroundColor: selectedTimeSlot == time
                                            ? AppColors.primaryBlue.withOpacity(0.1)
                                            : Colors.white,
                                        side: BorderSide(
                                          color: selectedTimeSlot == time
                                              ? AppColors.primaryBlue
                                              : Colors.grey.shade400,
                                        ),
                                      ),
                                      child: Text(
                                        time,
                                        style: TextStyle(
                                          color: selectedTimeSlot == time
                                              ? AppColors.primaryBlue
                                              : Colors.black,
                                        ),
                                      ),
                                    ),
                                  );
                                }).toList(),
                              ),
                            ),
                            const SizedBox(
                              height: 8,
                            ),
                            const Row(
                              children: [
                                Icon(
                                  Icons.location_on,
                                  size: 16,
                                  color: Colors.grey,
                                ),
                                Text(
                                  'ĐỊA CHỈ KHÁM',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(
                              width: 4,
                            ),
                            const Text(
                              'Phòng khám Đa khoa Mediplus',
                              style: TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            const Text(
                              'Tầng 2, Trung tâm thương mại Mandarin Garden 2, 99 phố Tân Mai, Hoàng Mai, Hà Nội',
                              style: TextStyle(
                                fontSize: 14,
                              ),
                            ),
                            const SizedBox(
                              height: 8,
                            ),
                            Row(
                              children: [
                                const Text(
                                  'Giá khám: ',
                                  style: TextStyle(fontWeight: FontWeight.bold),
                                ),
                                const Text(
                                  '350.000đ',
                                  style: TextStyle(
                                    color: Colors.red,
                                  ),
                                ),
                                const Spacer(),
                                GestureDetector(
                                  onTap: () {
                                    // Xử lý khi người dùng nhấn vào "Xem chi tiết"
                                  },
                                  child: const Text(
                                    'Xem chi tiết',
                                    style: TextStyle(
                                      color: Colors.blue,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
        // bottomNavigationBar: Container(
        //   height: 70,
        //   child: const NavigationBarWidget(),
        // ),
      ),
    );
  }
}
