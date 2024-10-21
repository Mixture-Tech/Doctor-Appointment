import 'package:app/ui/screens/NotificationScreen.dart';
import 'package:flutter/material.dart';
import 'package:app/ui/screens/HomeScreen.dart';
import 'package:app/ui/screens/AppointmentScreen.dart';
import 'package:app/ui/screens/PersonalInformationScreen.dart';
import 'package:app/ui/screens/ProfileScreen.dart';
import 'package:app/ui/widgets/NavigationBarWidget.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({Key? key}) : super(key: key);

  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    const HomeScreen(),
    const NotificationScreen(), // Thay thế bằng màn hình Thông báo thực tế
    const AppointmentScreen(),
    const Profile(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: IndexedStack(
        index: _selectedIndex,
        children: _pages,
      ),
      bottomNavigationBar: NavigationBarWidget(
        selectedIndex: _selectedIndex,
        onItemTapped: _onItemTapped,
      ),
    );
  }
}