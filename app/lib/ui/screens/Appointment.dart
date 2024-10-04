import 'package:flutter/material.dart';
import '../../styles/colors.dart';
import '../../styles/text.dart';
import '../widgets/HeaderWidget.dart';
import '../widgets/NavigationBarWidget.dart';

class Appointment extends StatefulWidget {
  const Appointment({super.key});

  @override
  State<Appointment> createState() => _AppointmentState();
}

class _AppointmentState extends State<Appointment> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: [
            HeaderWidget(
              onBackPressed: () {
                Navigator.pop(context);
              },
            ),
            // Adding a Row to display the text
            const Padding(
              padding: EdgeInsets.all(16.0), // Optional: Add padding around the text
              child: Row(
                children: [
                  Icon(
                      Icons.home,
                      color: AppColors.primaryBlue,
                  ),

                  const Text(
                      "/",
                      style: AppTextStyles.labelStyle,
                  ),

                  Expanded(
                    child: Text(
                      "Đặt lịch khám bệnh",
                      style: AppTextStyles.subHeaderStyle,
                      textAlign: TextAlign.left, // Align text to the left
                    ),
                  ),
                ],
              ),
            ),
            const Expanded(
              child: Stack(
                children: [
                  // Navigation bar widget on top
                  Positioned.fill(
                    child: NavigationBarWidget(),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
