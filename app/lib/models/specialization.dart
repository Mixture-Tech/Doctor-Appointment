import 'package:app/models/disease.dart';

class Specialization{

  Specialization({
   required this.id,
   required this.name,
   // required this.imgPath
    required this.diseases
  });

  final String id;
  final String name;
  // final String imgPath;
  final List<Disease> diseases;

  factory Specialization.fromJson(Map<String, dynamic> json) {
    return Specialization(
      id: json['specialization_id'] != null && json['specialization_id'] != '' ? json['specialization_id'] : 'Unknown ID',
      name: json['specialization_name'] != null && json['specialization_name'] != '' ? json['specialization_name'] : 'Unknown Name',
      diseases: json["diseases"] != null
          ? List<Disease>.from(json["diseases"].map((x) => Disease.fromJson(x)))
          : [], // Provide an empty list if 'diseases' is null
    );
  }


}