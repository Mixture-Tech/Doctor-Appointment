import 'package:app/models/error_code.dart';

class AuthenticationResponse {
  final String message;
  final String? accessToken;
  final String? refreshToken;
  final String? name;
  final String? role;
  final ErrorCode errorCode;

  AuthenticationResponse({
    required this.message,
    this.accessToken,
    this.refreshToken,
    this.name,
    this.role,
    required this.errorCode,
  });

  factory AuthenticationResponse.fromJson(Map<String, dynamic> json) {
    var errorCodeJson = json['error_code'];
    ErrorCode errorCode;
    if(errorCodeJson is String) {
      errorCode = ErrorCode(code: 200, message: "Success");
    }
    else{
      errorCode = ErrorCode.fromJson(json['error_code'] ?? {});
    }

    return AuthenticationResponse(
      message: json['message'] ?? 'No message',
      accessToken: json['access_token'],
      refreshToken: json['refresh_token'],
      name: json['name'],
      role: json['role'],
      errorCode: errorCode,
    );
  }
}