import 'package:app/styles/colors.dart';
import 'package:app/ui/widgets/ButtonWidget.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:flutter/material.dart';

class NotificationScreen extends StatelessWidget {
  const NotificationScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // Giả sử chúng ta có một danh sách các cuộc hẹn
    final List<Map<String, String>> appointments = [
      // Nếu có cuộc hẹn, thêm vào đây
      {'date': '30-08-2024', 'title': 'Mã xác nhận Đặt lịch khám thành công', 'description': '363528 là mã xác nhận đặt khám của Quý khách với Tiến sĩ, Bác sĩ chuyên khoa II Lê Quốc Việt vào ngày 31/08'},
    ];

    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            HeaderWidget(
              isHomeScreen: false,
              onIconPressed: () {
                // Navigator.pop(context);
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
                  ListView(
                    children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 24.0, horizontal: 15.0),
                        child: CustomElevatedButton(
                          text: 'Lịch hẹn của bạn',
                          onPressed: () {
                            // Xử lý khi nút được nhấn
                          },
                        ),
                      ),
                      _buildAppointmentsList(appointments),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAppointmentsList(List<Map<String, String>> appointments) {
    if (appointments.isEmpty) {
      return const Center(
        child: Padding(
          padding: EdgeInsets.all(15.0),
          child: Text(
            'Bạn chưa đặt cuộc hẹn nào',
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: AppColors.black,
            ),
          ),
        ),
      );
    }

    return Column(
      children: appointments.map((appointment) => Container(
        margin: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
        decoration: BoxDecoration(
          color: AppColors.lightGrey,
          borderRadius: BorderRadius.circular(8),
          boxShadow: [
            BoxShadow(
              color: AppColors.grey.withOpacity(0.5),
              spreadRadius: 2,
              blurRadius: 5,
              offset: const Offset(0, 3),
            ),
          ],
        ),
        child: ListTile(
          title: Text(appointment['date'] ?? ''),
          subtitle: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                appointment['title'] ?? '',
                style: const TextStyle(
                  color: AppColors.black,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 5),
              Text(
                appointment['description'] ?? '',
                style: const TextStyle(color: AppColors.black),
              ),
            ],
          ),
        ),
      )).toList(),
    );
  }
}