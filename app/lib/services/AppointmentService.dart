import 'package:app/models/appointment.dart';
import 'package:app/models/schedule.dart';
import 'package:app/models/user.dart';
import 'package:app/services/BaseApiService.dart';
import 'package:intl/intl.dart';

class AppointmentService {
  late final BaseApiService _apiService;

  static Future<AppointmentService> create() async {
    final apiService = await BaseApiService.create();
    return AppointmentService._(apiService);
  }

  AppointmentService._(this._apiService);

  Future<Appointment> createAppointment({
    required String doctorId,
    required Schedule schedule,
    required UserData user,
    required String type,
  }) async {
    try {
      print('Start Time: ${schedule.startTime}');
      print('End Time: ${schedule.endTime}');
      // Format dates and times
      final DateFormat dateFormatter = DateFormat('yyyy-MM-dd');
      final DateFormat timeFormatter = DateFormat('HH:mm:ss');

      print(user.dateOfBirth);
      print(user.gender);
      print(user.address);


      final response = await _apiService.post('/appointments/$doctorId', {
        'startTime': schedule.startTime, // Already in "HH:mm:ss" format
        'endTime': schedule.endTime, // Already in "HH:mm:ss" format
        'appointmentTakenDate': schedule.workingDate, // Already in "yyyy-MM-dd" format
        'patientName': user.username,
        'patientPhone': user.phone,
        'patientAddress': user.address,
        'patientDateOfBirth': user.dateOfBirth, // Already in "yyyy-MM-dd" format
        'patientGender': user.gender,
        'bookingType': type,
      });

      if (response['error_code'] == 'OK') {
        return Appointment.fromJson(response['data']);
      }
      throw Exception(response['message']);
    } catch (e) {
      print(e);
      // if (e is Map<String, dynamic>) {
      //   throw Exception(e['message'] ?? 'Unknown error occurred');
      // }
      if(e is Exception){
        throw e;
      }
      throw Exception('Failed to create appointment: $e');
    }
  }
}