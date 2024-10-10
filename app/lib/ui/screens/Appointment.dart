import 'package:flutter/material.dart';
import '../../styles/colors.dart';
import '../../styles/text.dart';
import '../widgets/ButtonWidget.dart';
import '../widgets/HeaderWidget.dart';
import '../widgets/NavigationBarWidget.dart';

class AppointmentScreen extends StatefulWidget {
  const AppointmentScreen({super.key});

  @override
  State<AppointmentScreen> createState() => _AppointmentScreenState();
}

class _AppointmentScreenState extends State<AppointmentScreen> {
  final List<String> timeSlots = [
    "07:00 - 07:30", "07:30 - 08:00", "08:00 - 08:30", "08:30 - 09:00",
    "09:00 - 09:30", "09:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00",
    "13:00 - 13:30", "13:30 - 14:00", "14:00 - 14:30", "14:30 - 15:00",
    "15:00 - 15:30", "15:30 - 16:00",
  ];
  int selectedTimeSlotIndex = -1;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            HeaderWidget(
              onBackPressed: () {
                Navigator.pop(context);
              },
            ),
            Expanded(
              child: Stack(
                fit: StackFit.expand,
                children: [
                  // Hình nền
                  Container(
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage("assets/images/Background_3.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                  // Nội dung chính
                  SingleChildScrollView(
                    child: Column(
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
                        // Hình ảnh chiếm khoảng 45% màn hình
                        Container(
                          height: MediaQuery.of(context).size.height * 0.45,
                          decoration: const BoxDecoration(
                            image: DecorationImage(
                              image: AssetImage("assets/images/Doctor.png"),
                              fit: BoxFit.cover,
                            ),
                          ),
                        ),

                        // Hộp thông tin lịch hẹn và thời gian đặt lịch
                        Transform.translate(
                          offset: Offset(0, -40),
                          child: Container(
                            padding: const EdgeInsets.fromLTRB(16, 36, 16, 16), // Tăng padding top để tạo khoảng trống cho thông tin bác sĩ
                            // margin: EdgeInsets.only(top: -20), // Kéo container này lên để che phần dưới của thông tin bác sĩ
                            decoration: BoxDecoration(
                              color: Colors.white,
                              borderRadius: BorderRadius.circular(70),
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.black.withOpacity(0.1),
                                  blurRadius: 10,
                                  spreadRadius: 5,
                                ),
                              ],
                            ),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                    "Ngày đặt lịch",
                                    style: TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.bold
                                    )
                                ),
                                const SizedBox(height: 10),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                                  children: [
                                    _buildDayButton("Thứ 2", 3),
                                    _buildDayButton("Thứ 3", 4),
                                    _buildDayButton("Thứ 4", 5, isSelected: true),
                                    _buildDayButton("Thứ 5", 6),
                                    _buildDayButton("Thứ 6", 7),
                                  ],
                                ),
                                const SizedBox(height: 20),
                                const Text(
                                    "Thời gian đặt lịch",
                                    style: TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.bold
                                    )
                                ),
                                const SizedBox(height: 10),

                                SizedBox(
                                  height: 220, // Bạn có thể điều chỉnh chiều cao này
                                  child: Wrap(
                                    spacing: 10, // Khoảng cách giữa các button theo chiều ngang
                                    runSpacing: 10, // Khoảng cách giữa các button theo chiều dọc
                                    children: List.generate(timeSlots.length, (index) {
                                      return _buildTimeButton(timeSlots[index], index);
                                    }),
                                  ),
                                ),

                                const SizedBox(height: 20),
                                Align(
                                  alignment: Alignment.bottomCenter,
                                  child: Padding(
                                    padding: const EdgeInsets.symmetric(vertical: 16),
                                    child: CustomElevatedButton(
                                      text: 'Tiếp theo',
                                      onPressed: () {},
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        // Thông tin bác sĩ đè lên ranh giới
                        Transform.translate(
                          offset: Offset(0, -620), // Di chuyển lên 40 pixel
                          child: Container(
                            // margin: EdgeInsets.symmetric(horizontal: 10),
                            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 30),
                            decoration: BoxDecoration(
                              color: AppColors.primaryBlue,
                              borderRadius: BorderRadius.circular(20),
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.black.withOpacity(0.1),
                                  blurRadius: 5,
                                  spreadRadius: 2,
                                ),
                              ],
                            ),
                            child: const Column(
                              children: [
                                Text(
                                  "PGS. TS. BS. Nguyễn Xuân Thành",
                                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: AppColors.white),
                                  textAlign: TextAlign.center,
                                ),
                                Text(
                                  "Nội tiêu hóa - Gan mật",
                                  style: TextStyle(fontSize: 14, color: AppColors.white)
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  // Thanh điều hướng
                  const NavigationBarWidget(),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDayButton(String day, int date, {bool isSelected = false}) {
    return ElevatedButton(
      onPressed: () {},
      style: ElevatedButton.styleFrom(
          backgroundColor: isSelected ? AppColors.secondaryYellow : AppColors.lightGrey,
          foregroundColor: isSelected ? AppColors.black : AppColors.black,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
          padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 0)
      ),
      child: Text(
          "$day\n$date",
          textAlign: TextAlign.center),
    );
  }

  Widget _buildTimeButton(String time, int index) {
    bool isSelected = index == selectedTimeSlotIndex;
    return ElevatedButton(
      onPressed: () {
        setState(() {
          selectedTimeSlotIndex = index;
        });
      },
      style: ElevatedButton.styleFrom(
          backgroundColor: isSelected ? AppColors.secondaryYellow : AppColors.lightGrey,
          foregroundColor: AppColors.black,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 7)
      ),
      child: Text(
        time,
        textAlign: TextAlign.center,
        style: TextStyle(fontSize: 12),
      ),
    );
  }
}