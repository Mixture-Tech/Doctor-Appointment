import 'dart:convert';
import 'package:app/models/auth_request.dart';
import 'package:app/models/auth_response.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

class AuthenticationService{
  static String baseUrl = dotenv.env['BASE_URL_API'] ?? '';

  Future<AuthenticationResponse> register(AuthenticationRequest request) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/auth/register'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode(request.toJson()),
      );

      final responseData = jsonDecode(response.body);

      return AuthenticationResponse.fromJson(responseData);
    } catch (e) {
      throw Exception('Failed to register: $e');
    }
  }

  Future<AuthenticationResponse> authenticate(AuthenticationRequest request) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/auth/authenticate'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode(request.toJson()),
      );

      final responseData = jsonDecode(utf8.decode(response.bodyBytes));

      return AuthenticationResponse.fromJson(responseData);
    } catch (e) {
      throw Exception('Failed to authenticate: $e');
    }
  }

  Future<AuthenticationResponse> forgotPassword(AuthenticationRequest request) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/auth/forgot-password'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode(request.toJson()),
      );

      final responseData = jsonDecode(response.body);

      return AuthenticationResponse.fromJson(responseData);
    } catch (e) {
      throw Exception('Failed to forgot password: $e');
    }
  }


}