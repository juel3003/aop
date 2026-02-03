import 'package:flutter/material.dart';
import 'menu_screen.dart';

void main() {
  runApp(PozoleDeliveryApp());
}

class PozoleDeliveryApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Pozole Delivery',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: MenuScreen(),
    );
  }
}
