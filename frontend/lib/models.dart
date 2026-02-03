class Order {
  final int? id;
  final String customerName;
  final String address;
  final double latitude;
  final double longitude;
  final String items;
  final String status;

  Order({
    this.id,
    required this.customerName,
    required this.address,
    required this.latitude,
    required this.longitude,
    required this.items,
    this.status = 'PENDING',
  });

  factory Order.fromJson(Map<String, dynamic> json) {
    return Order(
      id: json['id'],
      customerName: json['customerName'],
      address: json['address'],
      latitude: json['latitude'],
      longitude: json['longitude'],
      items: json['items'],
      status: json['status'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'customerName': customerName,
      'address': address,
      'latitude': latitude,
      'longitude': longitude,
      'items': items,
      'status': status,
    };
  }
}
