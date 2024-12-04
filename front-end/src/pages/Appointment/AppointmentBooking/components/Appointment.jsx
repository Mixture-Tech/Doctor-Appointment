import React, { useState } from 'react';
import Button from "../../../../components/Form/Button";
import MultipleSelect from "../../../../components/Form/Appointment/MultipleSelect";
import RadioButton from "../../../../components/Form/Appointment/RadioButton";
import AppointmentTextField from '../../../../components/Form/Appointment/AppointmentTextField';
import { RiAccountCircleLine, RiPhoneLine, RiMailLine, RiHome2Line, RiStethoscopeLine, RiCalendarLine, RiMapPinLine } from "react-icons/ri";

export default function AppointmentDetails() {
    const [selectedPatientType, setSelectedPatientType] = useState("Đặt cho mình");
    const [selectedGender, setSelectedGender] = useState("Nam");
    const [selectedCity, setSelectedCity] = useState("");
    const cities = ['Hồ Chí Minh', 'Hà Nội', 'Đà Nẵng'];
    const districts = ['Quận 1', 'Quận 3', 'Quận 10'];
    const Commune = ['Hà Đông', 'Đông Anh', 'Thanh Xuân'];

    return (
        <div className="w-full max-w-3xl mx-auto p-5 bg-white border border-gray-300 rounded-lg shadow-md font-sans mt-4 mb-4">
            <div className="max-w-lg mx-auto p-5 font-sans">
                {/* Price Section */}
                {/* <div className="mb-5">
                <label className="flex items-center">
                    <input type="radio" name="price" defaultChecked className="mr-2" />
                    Giá khám 180.000đ
                </label>
            </div> */}

                <div className="flex flex-col items-center">
                    <h3 className="text-lg font-semibold mb-3">Lựa chọn đối tượng</h3>
                    <div className="mb-4 flex gap-5">
                        <RadioButton
                            label="Đặt cho mình"
                            isChecked={selectedPatientType === "Đặt cho mình"}
                            onToggle={() => setSelectedPatientType("Đặt cho mình")}
                        />
                        <RadioButton
                            label="Đặt cho người thân"
                            isChecked={selectedPatientType === "Đặt cho người thân"}
                            onToggle={() => setSelectedPatientType("Đặt cho người thân")}
                        />
                    </div>
                </div>


                <form className="space-y-2">
                    {/* Additional Fields for Family Member */}
                    {selectedPatientType === "Đặt cho người thân" && (
                        <>
                            <h3 className="text-lg font-bold text-teal-600">Thông tin người đặt lịch</h3>
                            <AppointmentTextField icon={RiAccountCircleLine} type="text" placeholder="Họ tên người đặt lịch" className="mb-2" />
                            <AppointmentTextField icon={RiPhoneLine} type="text" placeholder="Số điện thoại" className="mb-2" />
                            <AppointmentTextField icon={RiMailLine} type="text" placeholder="Địa chỉ email" className="mb-2" />
                        </>
                    )}

                    {/* Common Fields */}
                    <h3 className="text-lg font-bold text-teal-600">Thông tin bệnh nhân</h3>
                    <AppointmentTextField icon={RiAccountCircleLine} type="text" placeholder="Họ tên bệnh nhân" className="mb-2" />
                    <p className="text-sm mb-4 text-gray-500">Hãy ghi rõ Họ Và Tên, viết hoa những chữ cái đầu tiên, ví dụ: Nguyễn Văn A</p>
                    <AppointmentTextField icon={RiPhoneLine} type="text" placeholder="Số điện thoại liên hệ (bắt buộc)" className="mb-2" />

                    {/* Gender Selection */}
                    <div>
                        <h3 className="text-lg font-semibold mb-2">Giới tính</h3>
                        <div className="flex gap-5">
                            <RadioButton
                                label="Nam"
                                isChecked={selectedGender === "Nam"}
                                onToggle={() => setSelectedGender("Nam")}
                            />
                            <RadioButton
                                label="Nữ"
                                isChecked={selectedGender === "Nữ"}
                                onToggle={() => setSelectedGender("Nữ")}
                            />
                        </div>
                    </div>

                    <AppointmentTextField icon={RiMailLine} type="text" placeholder="Địa chỉ email" className="mb-2" />
                    <AppointmentTextField icon={RiCalendarLine} type="date" placeholder="Chọn ngày sinh" className="mb-2" />
                    {/* Using MultipleSelect for City Selection */}
                    <MultipleSelect
                        options={cities}
                        value={selectedCity}
                        onChange={setSelectedCity}
                        placeholder="Chọn tỉnh thành"
                        className="mb-4"
                        icon={RiMapPinLine}
                    />

                    {/* District and Commune selection */}
                    <MultipleSelect
                        options={districts}
                        value={selectedCity}  // You can replace with a different state for district
                        onChange={setSelectedCity}
                        placeholder="Chọn Quận/Huyện"
                        className="mb-4"
                        icon={RiMapPinLine}
                    />

                    <MultipleSelect
                        options={Commune}
                        value={selectedCity}  // Replace with state for commune
                        onChange={setSelectedCity}
                        placeholder="Chọn Phường/Xã"
                        className="mb-4"
                        icon={RiMapPinLine}
                    />
                    <AppointmentTextField icon={RiStethoscopeLine} type="text" placeholder="Lý do khám bệnh" className="mb-2" />

                    {/* Payment Method Section */}
                    <div>
                        <h3 className="text-lg font-semibold">Hình thức thanh toán</h3>
                        <label className="flex items-center">
                            <input type="radio" name="paymentMethod" defaultChecked className="mr-2" />
                            Thanh toán sau tại cơ sở y tế
                        </label>
                    </div>

                    {/* Pricing Details */}
                    <div className="bg-gray-100 p-4 rounded-lg text-sm">
                        <div className="flex justify-between">
                            <span>Giá khám</span>
                            <span className="font-bold">180.000đ</span>
                        </div>
                        <div className="flex justify-between text-gray-500">
                            <span>Phí đặt lịch</span>
                            <span>Miễn phí</span>
                        </div>
                        <div className="flex justify-between font-bold text-red-600 mt-2">
                            <span>Tổng cộng</span>
                            <span>180.000đ</span>
                        </div>
                    </div>

                    {/* Reminder Section */}
                    <div className="bg-teal-50 p-4 rounded-lg text-sm text-teal-600">
                        <strong>LƯU Ý</strong>
                        <p>Thông tin anh/chị cung cấp sẽ được sử dụng làm hồ sơ khám bệnh:</p>
                        <ul className="list-disc pl-5">
                            <li>Ghi rõ họ và tên, viết hoa những chữ cái đầu tiên</li>
                            <li>Điền đầy đủ, đúng và vui lòng kiểm tra lại thông tin trước khi ấn "Đặt lịch khám"</li>
                        </ul>
                    </div>

                    {/* Terms and Conditions */}
                    <p className="text-sm text-gray-500 text-center">
                        Bằng việc xác nhận đặt khám, bạn đã hoàn toàn đồng ý với
                        <a href="#" className="text-teal-600 underline ml-1">Điều khoản sử dụng</a>.
                    </p>
                    {/* Căn giữa nút */}
                    <Button className="mx-auto block">Đặt Lịch Khám</Button>
                </form>

            </div>
        </div >
    );
}
