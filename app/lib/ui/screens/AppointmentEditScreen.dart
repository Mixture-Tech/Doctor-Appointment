import 'package:app/services/AppointmentService.dart';
import 'package:app/services/NotificationService.dart';
// import 'package:app/services/NotificationService.dart';
import 'package:app/services/NotifyService.dart';
import 'package:app/services/ProvinceGHNApiService.dart';
import 'package:app/services/StorageService.dart';
import 'package:app/ui/screens/BookingSuccessScreen.dart';
import 'package:app/ui/widgets/AddressDropdownField.dart';
import 'package:app/ui/widgets/ButtonWidget.dart';
import 'package:app/ui/widgets/DateTimePickerWidget.dart';
import 'package:app/ui/widgets/DoctorInfoWidget.dart';
import 'package:app/ui/widgets/HeaderWidget.dart';
import 'package:app/ui/widgets/RadioButtonWidget.dart';
import 'package:app/ui/widgets/TextFieldWidget.dart';
import 'package:app/utils/appointment_validator.dart';
import 'package:app/utils/auth_validator.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../../models/schedule.dart';
import '../../models/user.dart';
import '../../styles/colors.dart';
import '../../styles/text.dart';
import '../../utils/date_time.dart';
import 'NotificationScreen.dart';

class AppointmentEditScreen extends StatefulWidget {

  const AppointmentEditScreen({
    super.key,
  });

  @override
  State<AppointmentEditScreen> createState() => _AppointmentDetailScreenState();
}

class _AppointmentDetailScreenState extends State<AppointmentEditScreen> {
  // Form Controllers
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _patientNameController = TextEditingController();
  final TextEditingController _patientPhoneController = TextEditingController();
  final TextEditingController _dateController = TextEditingController();

  // Address Selection
  Map<String, dynamic>? _selectedProvince;
  Map<String, dynamic>? _selectedDistrict;
  Map<String, dynamic>? _selectedWard;

  Map<String, dynamic>? _originalProvince;
  Map<String, dynamic>? _originalDistrict;
  Map<String, dynamic>? _originalWard;

  // Form State
  DateTime? _selectedDate;
  TimeOfDay? _selectedTime;
  Gender _selectedGender = Gender.male;
  SingingCharacter _bookingType = SingingCharacter.SELF_BOOKING;
  bool _isLoading = false;
  bool _isAddressLoaded = false;
  String? _error;
  final List<String> availableDates = [
    '06/12/2025', '07/12/2025', '08/12/2025', '09/12/2025'
  ];

  final List<String> availableTimes = [
    '09:00 - 09:30', '10:00 - 10:30', '11:00 - 11:30', '12:00 - 12:30'
  ];

  // Services
  late Future<AppointmentService> _appointmentService;
  UserData? _currentUser;
  UserData? _userData;

  @override
  void initState() {
    super.initState();
    _appointmentService = AppointmentService.create();
    /*_loadUserData();*/
  }

  /*Future<void> _loadUserData() async {
    try {
      final storageService = await StorageService.getInstance();
      _currentUser = await storageService.getUserDataFromToken();

      if (_currentUser != null) {
        setState(() {
          // Fill thông tin người đặt/bệnh nhân dựa vào loại đặt lịch
          _updateFormFields();
        });
      }
    } catch (e) {
      print('Error loading user data: $e');
    }
  }*/

  // Cập nhật hàm _updateFormFields để không khóa trường ngày sinh
  /*void _updateFormFields() {
    if (_currentUser == null) return;

    setState(() {
      if(_bookingType == SingingCharacter.SELF_BOOKING){
        _updateSelfBookingFields();
      } else {
        _updateOtherBookingFields();
      }
    });
  }*/

  /*void _updateSelfBookingFields() {
    // Clear thông tin người đặt vì tự đặt không cần
    _nameController.clear();
    _phoneController.clear();
    _emailController.clear();

    // Cập nhật thông tin bệnh nhân từ currentUser
    _patientNameController.text = _currentUser?.username ?? '';
    _patientPhoneController.text = _currentUser?.phone ?? '';

    if (_currentUser?.dateOfBirth != null) {
      _selectedDate = DateTime.parse(_currentUser!.dateOfBirth!);
      _dateController.text = DateFormat('dd/MM/yyyy').format(_selectedDate!);
    }

    if (_currentUser?.gender != null) {
      _selectedGender = _currentUser!.gender?.toLowerCase() == 'nam'
          ? Gender.male
          : Gender.female;
    }

    // Khôi phục địa chỉ đã lưu
    if (_originalProvince != null && _originalDistrict != null && _originalWard != null) {
      setState(() {
        _selectedProvince = Map<String, dynamic>.from(_originalProvince!);
        _selectedDistrict = Map<String, dynamic>.from(_originalDistrict!);
        _selectedWard = Map<String, dynamic>.from(_originalWard!);
      });
    } else if (_currentUser?.address != null && _currentUser!.address!.isNotEmpty) {
      // Reset flag để cho phép xử lý địa chỉ lại
      _isAddressLoaded = false;
      _processAddress(_currentUser!.address!);
    }
  }

  void _updateOtherBookingFields() {
    // Lưu trữ địa chỉ hiện tại nếu có
    if (_selectedProvince != null && _selectedDistrict != null && _selectedWard != null) {
      _originalProvince = Map<String, dynamic>.from(_selectedProvince!);
      _originalDistrict = Map<String, dynamic>.from(_selectedDistrict!);
      _originalWard = Map<String, dynamic>.from(_selectedWard!);
    }

    // Cập nhật thông tin người đặt từ currentUser
    _nameController.text = _currentUser?.username ?? '';
    _phoneController.text = _currentUser?.phone ?? '';
    _emailController.text = _currentUser?.email ?? '';

    // Clear thông tin bệnh nhân
    _patientNameController.clear();
    _patientPhoneController.clear();
    _dateController.clear();
    _selectedDate = null;
    _selectedGender = Gender.male;

    // Clear địa chỉ
    setState(() {
      _selectedProvince = null;
      _selectedDistrict = null;
      _selectedWard = null;
    });
  }*/

  Future<void> _updateAppointment() async {

  }

  /*bool _validateForm() {
    final errors = AppointmentValidator.validateAppointmentForm(
      isOtherBooking: _bookingType == SingingCharacter.OTHER_BOOKING,
      patientName: _patientNameController.text,
      patientPhone: _patientPhoneController.text,
      dateOfBirth: _selectedDate,
      province: _selectedProvince,
      district: _selectedDistrict,
      ward: _selectedWard,
    );

    if (errors.isNotEmpty) {
      setState(() => _error = AppointmentValidator.getErrorMessage(errors));
      return false;
    }

    return true;
  }*/

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: [
            HeaderWidget(
              isHomeScreen: false,
              onIconPressed: () {
                Navigator.push(
                  context,
                  CupertinoPageRoute(
                    builder: (context) => NotificationScreen(),
                  ),
                );
              },
            ),
            Expanded(
              child: Stack(
                fit: StackFit.expand,
                children: [
                  _buildBackground(),
                  _buildContent(),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildBackground() {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/images/Background_3.png"),
          fit: BoxFit.cover,
        ),
      ),
    );
  }

  Widget _buildContent() {
    return ListView(
      children: [
        Column(
          children: [
            const SizedBox(height: 10),
            _buildMainContent(),
          ],
        ),
      ],
    );
  }

  Widget _buildMainContent() {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 18),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.circular(15),
                    child: Image.asset(
                      "assets/images/doctors/bui-gia-luong.png", // Use a placeholder image
                      width: 100,
                      height: 110,
                      fit: BoxFit.cover,
                    ),
                  ),
                  const SizedBox(width: 10),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const SizedBox(height: 4),
                        Text(
                          "Tạ Văn Tờ",
                          style: AppTextStyles.subHeaderStyle,
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                        const SizedBox(height: 4),
                        Text(
                          'Giá khám: 500.000 VND',
                          style: AppTextStyles.infoStyle,
                        ),
                        Text(
                          'Miễn phí đặt lịch',
                          style: AppTextStyles.infoStyle.copyWith(color: Colors.green),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
        SizedBox(height: 8),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 10),
          child: DropdownButtonFormField<String>(
            value: _selectedDate != null ? DateFormat('dd/MM/yyyy').format(_selectedDate!) : null,
            onChanged: (String? newValue) {
              setState(() {
                _selectedDate = DateTime.parse(newValue!);
              });
            },
            decoration: InputDecoration(
              labelText: 'Chọn ngày',
              border: OutlineInputBorder(),
              contentPadding: EdgeInsets.symmetric(vertical: 10, horizontal: 12), // Giảm khoảng cách trong
            ),
            items: availableDates.map((String date) {
              return DropdownMenuItem<String>(
                value: date,
                child: Text(date),
              );
            }).toList(),
          ),
        ),
// Add Time Selector
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 10),
          child: DropdownButtonFormField<String>(
            value: _selectedTime != null ? _selectedTime!.format(context) : null,
            onChanged: (String? newValue) {
              setState(() {
                _selectedTime = TimeOfDay.fromDateTime(DateFormat.jm().parse(newValue!));
              });
            },
            decoration: InputDecoration(
              labelText: 'Chọn giờ',
              border: OutlineInputBorder(),
              contentPadding: EdgeInsets.symmetric(vertical: 10, horizontal: 12), // Giảm khoảng cách trong
            ),
            items: availableTimes.map((String time) {
              return DropdownMenuItem<String>(
                value: time,
                child: Text(time),
              );
            }).toList(),
          ),
        ),

        TypeSelectorWidget(
          character: _bookingType,
          onChanged: (value) {
            if (value != null) {
              setState(() {
                _bookingType = value;
                // Clear tất cả các controller trước khi cập nhật form mới
                _nameController.clear();
                _phoneController.clear();
                _emailController.clear();
                _patientNameController.clear();
                _patientPhoneController.clear();
                _dateController.clear();
                _selectedDate = null;
                _selectedGender = Gender.male;
                _selectedProvince = null;
                _selectedDistrict = null;
                _selectedWard = null;
              });
              // Gọi updateFormFields sau khi đã clear các trường
              /*_updateFormFields();*/
            }
          },
        ),
        if (_bookingType == SingingCharacter.OTHER_BOOKING) _buildBookerInfo(),
        _buildPatientInfo(),
        if (_error != null) _buildErrorMessage(),
        _buildFooterContent(),
      ],
    );
  }

  Widget _buildErrorMessage() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 18),
      child: Text(
        _error!,
        style: const TextStyle(color: Colors.red),
      ),
    );
  }

  Widget _buildFooterContent() {
    return Column(
      children: [
        const SizedBox(height: 20),
        if (_isLoading)
          const Center(child: CircularProgressIndicator())
        else
          CustomElevatedButton(
            text: 'Cập nhật',
            onPressed: _updateAppointment,
          ),
        const SizedBox(height: 40),
      ],
    );
  }

  Widget _buildBookerInfo(){
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.only(left: 18, bottom: 5),
          child: Text(
            'Thông tin người đặt',
            style: TextStyle(
                color: AppColors.primaryBlue,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
        TextFieldWidget(
          controller: _nameController,
          hintText: 'Họ và tên',
          prefixIcon: Icons.person_outline,
          enabled: false,
        ),
        SizedBox(height: 10),
        TextFieldWidget(
          controller: _phoneController,
          hintText: 'Số điện thoại',
          prefixIcon: Icons.phone_outlined,
          enabled: false,
        ),
        SizedBox(height: 10),
        TextFieldWidget(
          controller: _emailController,
          hintText: 'Email',
          prefixIcon: Icons.email_outlined,
          enabled: false,
        ),
        SizedBox(height: 20),
      ],
    );
  }
  Widget _buildPatientInfo() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.only(left: 18, bottom: 5),
          child: Text(
            _bookingType == SingingCharacter.SELF_BOOKING
                ? 'Thông tin bệnh nhân'
                : 'Thông tin người thân',
            style: const TextStyle(
                color: AppColors.primaryBlue,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
        TextFieldWidget(
          controller: _patientNameController,
          hintText: 'Họ tên bệnh nhân',
          prefixIcon: Icons.person_outline,
          isRequired: true,
          enabled: false,
          readOnly: true,
        ),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 6),
          child: GenderSelectorWidget(
            selectedGender: _selectedGender,
            onChanged: (Gender value) {
              setState(() {
                _selectedGender = value; // Update selected gender
              });
            },
            enabled: false,
            readOnly: true,
          ),
        ),
        TextFieldWidget(
          controller: _patientPhoneController,
          hintText: 'Số điện thoại bệnh nhân',
          prefixIcon: Icons.phone_outlined,
          isRequired: true,
          enabled: false,
          readOnly: true,
        ),
        const SizedBox(height: 10),
        DatePickerTextField(
          controller: _dateController,
          hintText: 'Ngày/tháng/năm sinh',
          prefixIcon: Icons.calendar_today_outlined,
          initialDate: _selectedDate,
          isRequired: true,
          onDateSelected: (DateTime selectedDate) {
            setState(() {
              _selectedDate = selectedDate;
            });
          },
          enabled: false,
          readOnly: true,
        ),
        const SizedBox(height: 10),
        AddressDropdownField(
          key: ValueKey('province-${_selectedProvince?.toString()??''}'),
          hintText: 'Chọn tỉnh/thành phố',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          initialValue: _selectedProvince,
          fetchItems: ProvinceGHNApiService.getProvinces,
          onChanged: (value) {
            setState(() {
              _selectedProvince = value;
              _selectedDistrict = null;
              _selectedWard = null;
            });
          },
          enabled: false, // Luôn bật để hiển thị dữ liệu
          readOnly: true, // Không cho chọn nếu là SELF_BOOKING
        ),
        const SizedBox(height: 10),
        AddressDropdownField(
          key: ValueKey('district-${_selectedDistrict?.toString() ?? ''}-${_selectedProvince?.toString() ?? ''}'),
          hintText: 'Chọn quận/huyện',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          initialValue: _selectedDistrict,
          fetchItems: () => _selectedProvince != null
              ? ProvinceGHNApiService.getDistricts(_selectedProvince!['ProvinceID'])
              : Future.value([]),
          onChanged: (value) {
            setState(() {
              _selectedDistrict = value;
              _selectedWard = null;
            });
          },
          enabled: false, // Luôn bật để hiển thị dữ liệu
          readOnly: true, // Không cho chọn nếu là SELF_BOOKING
        ),
        const SizedBox(height: 10),
        AddressDropdownField(
          key: ValueKey('ward-${_selectedWard?.toString() ?? ''}-${_selectedDistrict?.toString() ?? ''}'),
          hintText: 'Chọn phường/xã',
          prefixIcon: Icons.location_on_outlined,
          isRequired: true,
          initialValue: _selectedWard,
          fetchItems: () => _selectedDistrict != null
              ? ProvinceGHNApiService.getWards(_selectedDistrict!['DistrictID'])
              : Future.value([]),
          onChanged: (value) {
            setState(() {
              _selectedWard = value;
            });
          },
          enabled: false, // Luôn bật để hiển thị dữ liệu
          readOnly: true, // Không cho chọn nếu là SELF_BOOKING
        ),
      ],
    );
  }
}