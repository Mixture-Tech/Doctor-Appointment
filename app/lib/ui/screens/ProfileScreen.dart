import 'package:app/styles/colors.dart';
import 'package:app/styles/text.dart';
import 'package:app/ui/screens/PersonalInformationScreen.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:app/ui/screens/LoginScreen.dart';
import 'package:app/ui/widgets/NavigationBarWidget.dart';
import 'package:flutter/material.dart';
import 'package:app/ui/widgets/ProfileOptionWidget.dart';

class Profile extends StatefulWidget {
  const Profile({super.key});

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {

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
                Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(builder: (context) =>
                      const LoginScreen()), // Chuyển đến trang đăng nhập
                );
              },
              // customIcon: Icons.menu,
            ),
            // Phần thông tin cá nhân
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

                  // Thêm widget lên trên background
                  SingleChildScrollView(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        SizedBox(height: 20),

                        // Hình đại diện và tên người dùng
                        CircleAvatar(
                          radius: 50,
                          backgroundImage: AssetImage("assets/images/Personal_Avatar.png"), // Thay backgroundColor bằng backgroundImage
                        ),
                        SizedBox(height: 10),
                        Text(
                          'Nguyễn Văn A',
                          style: AppTextStyles.labelStyle.copyWith(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                            color: AppColors.accentBlue,
                          ),
                        ),
                        SizedBox(height: 50),

                        ProfileOptionWidget(
                          icon: Icons.person,
                          label: 'Thông tin cá nhân',
                          onTap: () {
                           Navigator.push(context, 
                           MaterialPageRoute(builder: (context) => const PersonalInfoScreen()),
                           );
                          },
                        ),
                        ProfileOptionWidget(
                          icon: Icons.family_restroom,
                          label: 'Thành viên gia đình',
                          onTap: () {
                            // Xử lý khi người dùng bấm vào mục "Thông tin cá nhân"
                          },
                        ),
                        ProfileOptionWidget(
                          icon: Icons.history,
                          label: 'Lịch sử khám bệnh',
                          onTap: () {
                            // Xử lý khi người dùng bấm vào mục "Thông tin cá nhân"
                          },
                        ),
                        ProfileOptionWidget(
                          icon: Icons.folder,
                          label: 'Hồ sơ khám bệnh',
                          onTap: () {
                            // Xử lý khi người dùng bấm vào mục "Thông tin cá nhân"
                          },
                        ),
                      ],
                    ),
                  ),

                  // Navigation Bar ở dưới
                  // Align(
                  //   alignment: Alignment.bottomCenter,
                  //   child: NavigationBarWidget(),
                  // ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
