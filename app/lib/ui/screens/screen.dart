import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:app/ui/widgets/NavigationBarWidget.dart';
import 'package:flutter/material.dart';

class MyScreen extends StatefulWidget {
  const MyScreen({super.key});

  @override
  State<MyScreen> createState() => _MyScreenState();
}

class _MyScreenState extends State<MyScreen> {
  @override
  Widget build(BuildContext context) {
    return const SafeArea(
      child: Scaffold(
          body:
          Column(
            children: [
              HeaderWidget(
                onBackPressed: null,
              ),
              Expanded(child:
              Stack(
                children: [
                  // Background image
                  DecoratedBox(
                    decoration: BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage("assets/images/Background_1.png"),
                        fit: BoxFit.cover,
                      ),
                    ),
                    child: Center(
                       child: FlutterLogo(
                         size: 200,
                       ),
                     ),
                  ),
                  // Navigation bar widget on top
                  Positioned.fill(
                    child: NavigationBarWidget(),
                  ),
                ],
              ),
              ),
            ],
          )
      ),
    );
  }
}
