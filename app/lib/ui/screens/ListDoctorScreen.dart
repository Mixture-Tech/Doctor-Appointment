import 'package:app/ui/screens/AppointmentScreen.dart';
import 'package:app/ui/widgets/DoctorInfoWidget.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../../styles/colors.dart';
import '../../styles/text.dart';
import '../widgets/DoctorCardWidget.dart';

class ListDoctorScreen extends StatefulWidget {
  const ListDoctorScreen({
    super.key,
  });

  @override
  State<ListDoctorScreen> createState() => _ListDoctorScreenState();
}

class _ListDoctorScreenState extends State<ListDoctorScreen> {
  void handleDoctorCardTap(String doctorName) {
    Navigator.push(
      context,
      CupertinoPageRoute(
        builder: (context) => const AppointmentScreen(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            HeaderWidget(
              isHomeScreen: true,
              onIconPressed: () {
                // Navigator.pop(context);
              },
            ),
            Expanded(
              child: Stack(
                fit: StackFit.expand,
                children: [
                  Container(
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage("assets/images/Background_2.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                  ListView(
                    children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                        child: Column(
                          children: [
                            DoctorCardWidget(
                              name: 'TS. BS. Phạm Minh Anh',
                              imageUrl: 'assets/images/bs1.jpg',
                              facility: 'Cơ Xương Khớp',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('TS. BS. Phạm Minh Anh'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'BS. Nguyễn Thị Kim Hoa',
                              imageUrl: 'assets/images/bs2.jpg',
                              facility: 'Thần Kinh',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('BS. Nguyễn Thị Kim Hoa'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'PGS. TS. BS. Trần Văn B',
                              imageUrl: 'assets/images/bs3.png',
                              facility: 'Tim mạch',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('PGS. TS. BS. Trần Văn B'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'PGS. TS. BS. Trần Văn B',
                              imageUrl: 'assets/images/bs3.png',
                              facility: 'Tim mạch',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('PGS. TS. BS. Trần Văn B'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'PGS. TS. BS. Trần Văn B',
                              imageUrl: 'assets/images/bs3.png',
                              facility: 'Tim mạch',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('PGS. TS. BS. Trần Văn B'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'PGS. TS. BS. Trần Văn B',
                              imageUrl: 'assets/images/bs3.png',
                              facility: 'Tim mạch',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('PGS. TS. BS. Trần Văn B'),
                            ),
                            const SizedBox(height: 10),
                            DoctorCardWidget(
                              name: 'PGS. TS. BS. Trần Văn B',
                              imageUrl: 'assets/images/bs3.png',
                              facility: 'Tim mạch',
                              description: 'ABCXYZ',
                              onTap: () => handleDoctorCardTap('PGS. TS. BS. Trần Văn B'),
                            ),
                            const SizedBox(height: 100),
                          ],
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
    );
  }
}
