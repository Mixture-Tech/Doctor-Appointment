import 'dart:convert';
import 'package:app/services/BaseApiService.dart';
import '../models/disease.dart';
import '../models/specialization.dart';

class ChatbotService {
  late final BaseApiService _apiService;

  // Factory constructor
  static Future<ChatbotService> create() async {
    final apiService = await BaseApiService.create();
    return ChatbotService._(apiService);
  }

  // Private constructor
  ChatbotService._(this._apiService);

  Future<Disease?> predictDiseaseBySymptoms(String symptoms) async {
    try {
      final request = {
        'symptomName': symptoms
      };

      final response = await _apiService.post('/chatbot/predict-disease', request);

      if (response['error_code'] == 'OK' && response['data'] != null) {
        return Disease.fromJson(response['data']);
      } else {
        print('Error in prediction: ${response['message']}');
        return null;
      }
    } catch (e) {
      print('Exception in predictDiseaseBySymptoms: $e');
      return null;
    }
  }

  Future<Specialization?> getSpecializationDetails(int diseaseId) async {
    try {
      final response = await _apiService.get('/specializations/disease/$diseaseId');

      if (response['error_code'] == 'OK' && response['data'] != null) {
        return Specialization.fromJson(response['data']);
      } else {
        print('Error fetching specialization: ${response['message']}');
        return null;
      }
    } catch (e) {
      print('Exception in getSpecializationDetails: $e');
      return null;
    }
  }
}