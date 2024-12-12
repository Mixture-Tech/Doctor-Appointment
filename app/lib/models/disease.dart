import 'package:app/models/specialization.dart';

class Disease{
  Disease({
    required this.id,
    required this.name,
    required this.causeOfDisease,
    // required this.specialization
  });

  final int id;
  final String name;
  final String causeOfDisease;
  // final Specialization specialization;

  factory Disease.fromJson(Map<String, dynamic> json){
    return Disease(
      id: json['disease_id'] != null && json['disease_id'] != '' ? json['disease_id'] : 'Unknown ID',
      name: json['disease_vie_name'] != null && json['disease_vie_name'] != '' ? json['disease_vie_name'] : 'Unknown Name',
      causeOfDisease: json['cause_of_disease'] != null && json['cause_of_disease'] != '' ? json['cause_of_disease'] : 'Unknown Cause Of Disease',
      // specialization: Specialization.fromJson(json['specialization']),
    );
  }
}