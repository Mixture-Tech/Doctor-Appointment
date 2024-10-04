import 'package:flutter/material.dart';
import 'package:app/styles/colors.dart';

class HeaderWidget extends StatelessWidget {
  final VoidCallback? onBackPressed;

  const HeaderWidget({
    super.key,
    this.onBackPressed,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.2),
            spreadRadius: 1,
            blurRadius: 3,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Stack(
        children: [
          Align(
            alignment: Alignment.center,
            child: Image.asset(
              'assets/images/Logo.png',
              height: 60,
              fit: BoxFit.contain,
            ),
          ),
          Positioned(
            left: 0,
            top: 5,
            child: IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: onBackPressed,
              color: AppColors.primaryBlue,
            ),
          ),
        ],
      ),
    );
  }
}
