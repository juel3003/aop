import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:frontend/main.dart';

void main() {
  testWidgets('Menu loads smoke test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(PozoleDeliveryApp());

    // Verify that the menu shows up.
    expect(find.text('Pozole Menu'), findsOneWidget);
    expect(find.text('Pozole Rojo'), findsOneWidget);
  });
}
