import React from 'react';
import { FaClock, FaCalendarAlt } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export default function List({ appointments }) {
    const navigate = useNavigate();

    const handleButtonClick = (appointmentId) => {
        navigate(`/chi-tiet-lich-hen/${appointmentId}`); // Điều hướng kèm theo ID
    };

    return (
        <div>
            <h2 className="mb-8 font-bold text-2xl font-semibold">DANH SÁCH LỊCH HẸN</h2>
            {appointments.length === 0 ? (
                <p>Hiện không có lịch hẹn nào.</p>
            ) : (
                appointments.map((appointment, index) => (
                    <div
                        key={index}
                        className="flex flex-col border border-gray-300 rounded-lg p-5 mb-5 shadow-md max-w-5xl mx-auto"
                    >
                        <div className="flex items-center">
                            <div className="mr-5">
                                <img
                                    src={appointment.doctorImage || '/default-avatar.png'} // URL hình bác sĩ (hoặc mặc định)
                                    alt={appointment.doctor}
                                    className="w-16 h-16 rounded-full"
                                />
                            </div>
                            <div className="flex-1">
                                <p>Bệnh nhân: {appointment.patientName}</p>
                                <p>
                                    Bác sĩ: <span className="text-teal-500">{appointment.doctorName}</span>
                                </p>
                                <p>Nơi khám: {appointment.location}</p>
                                <p>Ngày tạo lịch hẹn: {appointment.createdAt}</p>
                            </div>
                            <div className="flex flex-col items-center">
                                <span className="text-yellow-500 font-bold underline mb-3">
                                    {appointment.status === 'CONFIRMED' ? 'Đã đặt lịch hẹn' : 'Đã hủy'}
                                </span>
                                <div className="flex items-center text-sm">
                                    <FaClock className="mr-2 text-yellow-500" />
                                    {appointment.time}
                                </div>
                                <div className="flex items-center text-sm mt-2">
                                    <FaCalendarAlt className="mr-2 text-yellow-500" />
                                    {appointment.date}
                                </div>
                            </div>
                        </div>
                        <div className="border-t border-gray-300 mt-3 pt-3 text-right">
                            <a
                                onClick={() => handleButtonClick(appointment.id)}
                                className="text-teal-500 hover:underline cursor-pointer"
                            >
                                Chi tiết lịch hẹn
                            </a>
                        </div>
                    </div>
                ))
            )}
        </div>
    );
}
