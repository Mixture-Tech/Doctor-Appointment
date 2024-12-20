import React, { useState } from "react";

export default function EditAppointment({ onSave, onCancel }) {
    // Dữ liệu mẫu lịch hẹn
    const appointmentData = {
        id: 1,
        date: "20-12-2024",
        time: "09:00 - 10:00",
        patient: "Nguyễn Văn A",
        type: "Khám định kỳ",
        doctor: "Nguyễn Văn Hùng", // Chỉnh sửa: "Bác sĩ" giờ là lựa chọn từ danh sách
    };

    // Dữ liệu danh sách bác sĩ
    const doctors = [
        "Đã khám",
        "Chưa khám",
        "Không có mặt"
    ];

    const [appointment, setAppointment] = useState(appointmentData);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAppointment({
            ...appointment,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(appointment); // Gọi hàm onSave khi lưu thông tin
    };

    return (
        <div className="p-4 space-y-4 max-w-2xl mx-auto">
            {/* Form chỉnh sửa lịch hẹn */}
            <div className="bg-white p-4 rounded-lg shadow">
                <h2 className="text-lg font-semibold mb-4">Chỉnh sửa lịch hẹn</h2>
                <form onSubmit={handleSubmit}>
                    <div className="space-y-3">
                        <div className="flex items-center space-x-3">
                            <label htmlFor="date" className="w-1/4 text-gray-700">Ngày</label>
                            <input
                                type="text"
                                id="date"
                                name="date"
                                value={appointment.date}
                                onChange={handleChange}
                                className="border border-gray-300 p-2 rounded-md w-3/4 text-sm disabled:border-gray-400 disabled:bg-gray-200"
                                disabled={true}
                            />
                        </div>
                        <div className="flex items-center space-x-3">
                            <label htmlFor="time" className="w-1/4 text-gray-700">Giờ</label>
                            <input
                                type="text"
                                id="time"
                                name="time"
                                value={appointment.time}
                                onChange={handleChange}
                                className="border border-gray-300 p-2 rounded-md w-3/4 text-sm disabled:border-gray-400 disabled:bg-gray-200"
                                disabled={true}
                            />
                        </div>
                        <div className="flex items-center space-x-3">
                            <label htmlFor="patient" className="w-1/4 text-gray-700">Bệnh nhân</label>
                            <input
                                type="text"
                                id="patient"
                                name="patient"
                                value={appointment.patient}
                                onChange={handleChange}
                                className="border border-gray-300 p-2 rounded-md w-3/4 text-sm disabled:border-gray-400 disabled:bg-gray-200"
                                disabled={true}
                            />
                        </div>
                        <div className="flex items-center space-x-3">
                            <label htmlFor="type" className="w-1/4 text-gray-700">Loại</label>
                            <input
                                type="text"
                                id="type"
                                name="type"
                                value={appointment.type}
                                onChange={handleChange}
                                className="border border-gray-300 p-2 rounded-md w-3/4 text-sm disabled:border-gray-400 disabled:bg-gray-200"
                                disabled={true}
                            />
                        </div>
                        <div className="flex items-center space-x-3">
                            <label htmlFor="doctor" className="w-1/4 text-gray-700">Bác sĩ</label>
                            <select
                                id="doctor"
                                name="doctor"
                                value={appointment.doctor}
                                onChange={handleChange}
                                className="border border-gray-300 p-2 rounded-md w-3/4 text-sm disabled:border-gray-400 disabled:bg-gray-200"
                            >
                                {doctors.map((doctor, index) => (
                                    <option key={index} value={doctor}>
                                        {doctor}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    {/* Các nút hành động */}
                    <div className="mt-4 flex justify-end space-x-4">
                        <button
                            type="button"
                            onClick={onCancel}
                            className="bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 text-sm"
                        >
                            Hủy
                        </button>
                        <button
                            type="submit"
                            className="bg-[#48CFCB] text-white px-4 py-2 rounded-md hover:bg-[#48CFCB] text-sm"
                        >
                            Lưu
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
