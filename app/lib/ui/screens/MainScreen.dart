import 'package:app/ui/screens/ChatbotScreen.dart';
import 'package:app/ui/screens/ListDoctorScreen.dart';
import 'package:flutter/material.dart';
import 'package:app/ui/screens/HomeScreen.dart';
import 'package:app/ui/screens/NotificationScreen.dart';
import 'package:app/ui/screens/AppointmentScreen.dart';
import 'package:app/ui/screens/ProfileScreen.dart';
import 'package:app/ui/screens/AnswerScreen.dart'; // Đảm bảo thêm AnswerScreen vào import
import 'package:app/ui/widgets/NavigationBarWidget.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({Key? key}) : super(key: key);

  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;
  bool _isInAnswerScreen = false; // Biến theo dõi nếu đang ở AnswerScreen

  final List<GlobalKey<NavigatorState>> _navigatorKeys = [
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>(),
    GlobalKey<NavigatorState>(),
  ];

  void _onItemTapped(int index) {
    // Nếu đang ở AnswerScreen và người dùng nhấn vào chatbot thì ngừng việc điều hướng
    if (index == 3 && _isInAnswerScreen) {
      return;  // Không chuyển về ChatbotScreen khi đang ở AnswerScreen
    }

    if (index == _selectedIndex) {
      // Nếu đang ở tab hiện tại, pop về màn hình đầu tiên
      _navigatorKeys[index].currentState?.popUntil((route) => route.isFirst);
    } else {
      setState(() {
        // Cập nhật trạng thái khi chuyển tab
        if (index == 3) {
          _isInAnswerScreen = true; // Đang ở AnswerScreen khi chọn Chatbot tab
        } else if (_selectedIndex == 3) {
          _isInAnswerScreen = false; // Không còn ở AnswerScreen khi chuyển tab khác
        }

        _selectedIndex = index;
      });
    }
  }

  Widget _buildOffstageNavigator(int index) {
    return Offstage(
      offstage: _selectedIndex != index,
      child: TabNavigator(
        navigatorKey: _navigatorKeys[index],
        tabItem: TabItem.values[index],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        final isFirstRouteInCurrentTab =
        !await _navigatorKeys[_selectedIndex].currentState!.maybePop();
        if (isFirstRouteInCurrentTab) {
          if (_selectedIndex != 0) {
            _onItemTapped(0);
            return false;
          }
        }
        return isFirstRouteInCurrentTab;
      },
      child: Scaffold(
        body: Stack(
          children: <Widget>[
            _buildOffstageNavigator(0),
            _buildOffstageNavigator(1),
            _buildOffstageNavigator(2),
            _buildOffstageNavigator(3),
            _buildOffstageNavigator(4),
          ],
        ),
        bottomNavigationBar: NavigationBarWidget(
          selectedIndex: _selectedIndex,
          onItemTapped: _onItemTapped,
        ),
      ),
    );
  }
}

enum TabItem { home, notification, appointment, chatbot, profile }

class TabNavigator extends StatelessWidget {
  final GlobalKey<NavigatorState> navigatorKey;
  final TabItem tabItem;

  const TabNavigator({
    Key? key,
    required this.navigatorKey,
    required this.tabItem,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Widget child;
    switch (tabItem) {
      case TabItem.home:
        child = const HomeScreen();
        break;
      case TabItem.notification:
        child = const NotificationScreen();
        break;
      case TabItem.appointment:
        child = const ListDoctorScreen();
        break;
      case TabItem.chatbot:
        child = const ChatbotScreen();
        break;
      case TabItem.profile:
        child = const ProfileScreen();
        break;
    }

    return Navigator(
      key: navigatorKey,
      onGenerateRoute: (routeSettings) {
        return MaterialPageRoute(builder: (context) => child);
      },
    );
  }
}
