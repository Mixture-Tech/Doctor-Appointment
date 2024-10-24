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
    // Xử lý sự kiện khi nhấn vào DoctorCard
    print('Doctor card tapped: $doctorName');
    // Bạn có thể thêm logic khác ở đây nếu cần
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: <Widget>[
            HeaderWidget(
              isHomeScreen: false,
              onIconPressed: () {
                Navigator.pop(context);
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
                                "Danh sách bác sĩ",
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
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 10),
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
