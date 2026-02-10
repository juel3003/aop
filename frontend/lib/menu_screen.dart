import 'package:flutter/material.dart';
import 'order_screen.dart';

class MenuScreen extends StatelessWidget {
  final List<String> pozoleOptions = [
    'Pozole Rojo',
    'Pozole Verde',
    'Pozole Blanco',
    'Pozole de Camaron'
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Pozole Menu')),
      body: ListView.builder(
        itemCount: pozoleOptions.length,
        itemBuilder: (context, index) {
          return Card(
            child: ListTile(
              title: Text(pozoleOptions[index]),
              leading: Icon(Icons.restaurant),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => OrderScreen(selectedItem: pozoleOptions[index]),
                  ),
                );
              },
            ),
          );
        },
      ),
    );
  }
}
