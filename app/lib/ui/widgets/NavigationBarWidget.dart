import 'package:app/styles/colors.dart';
import 'package:flutter/material.dart';

class NavigationBarWidget extends StatefulWidget {
  const NavigationBarWidget({super.key});

  @override
  State<NavigationBarWidget> createState() => _NavigationBarWidgetState();
}

class _NavigationBarWidgetState extends State<NavigationBarWidget> {
  int _selectedIndex = 0;
  final List<Widget> _pages = [
    // Center(child: Text('Trang chủ')),
    // Center(child: Text('Thông báo')),
    // Center(child: Text('Lịch hẹn')),
    // Center(child: Text('Cá nhân')),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Expanded(
          child: IndexedStack(
            index: _selectedIndex,
            children: _pages,
          ),
        ),
        BottomNavigationBar(
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: 'Trang chủ',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.notifications),
              label: 'Thông báo',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.calendar_month),
              label: 'Lịch hẹn',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.person),
              label: 'Cá nhân',
            )
          ],
          currentIndex: _selectedIndex,
          selectedItemColor: AppColors.primaryBlue,
          unselectedItemColor: AppColors.black,
          selectedLabelStyle: const TextStyle(fontSize: 15),
          onTap: _onItemTapped,
          iconSize: 30,
        ),
      ],
    );
  }
}
