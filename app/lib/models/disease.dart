class Disease{
  Disease({
    required this.id,
    required this.name,
  });

  final int id;
  final String name;

  factory Disease.fromJson(Map<String, dynamic> json){
    return Disease(
      id: json['disease_id'] != null && json['disease_id'] != '' ? json['disease_id'] : 'Unknown ID',
      name: json['disease_vie_name'] != null && json['disease_vie_name'] != '' ? json['disease_vie_name'] : 'Unknown Name',
    );
  }
}