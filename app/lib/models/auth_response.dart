import 'package:app/models/error_code_auth.dart';
import 'package:app/models/user.dart';

class AuthenticationResponse {
  final String message;
  final String? accessToken;
  final String? refreshToken;
  final UserData? userData;
  final ErrorCode errorCode;

  AuthenticationResponse({
    required this.message,
    this.accessToken,
    this.refreshToken,
    this.userData,
    required this.errorCode,
  });

  factory AuthenticationResponse.fromJson(Map<String, dynamic> json) {
    var errorCodeJson = json['error_code'];
    ErrorCode errorCode;
    if(errorCodeJson is String) {
      errorCode = ErrorCode(code: 200, message: "Success");
    } else {
      errorCode = ErrorCode.fromJson(json['error_code'] ?? {});
    }

    return AuthenticationResponse(
      message: json['message'] ?? 'No message',
      accessToken: json['access_token'],
      refreshToken: json['refresh_token'],
      userData: json['user_data'] != null ? UserData.fromJson(json['user_data']) : null,
      errorCode: errorCode,
    );
  }
}