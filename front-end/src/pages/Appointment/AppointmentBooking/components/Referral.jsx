import React from 'react';
import { FaClock, FaCalendarAlt } from 'react-icons/fa';
import DinhThiThuHuong from '../../../../assets/images/doctors/dinh-thi-thu-huong.png';

export default function DoctorReferral() {
    return (
        <div className="w-full bg-[#F5F5F5] py-4 px-5 flex justify-center">

            <div className="flex items-center max-w-screen-lg">
                <img
                    src={DinhThiThuHuong}
                    alt="Bác sĩ Đinh Thị Thu Hương"
                    className="w-20 h-20 rounded-full mr-4"
                />
                <div>
                    <h2 className="text-center text-xl font-semibold">ĐẶT LỊCH KHÁM</h2>
                    <h3 className="text-lg font-medium">Bác sĩ Đinh Thị Thu Hương</h3>
                    {/* Giờ khám */}
                    <p className="my-1 text-[#ff9900] flex items-center">
                        <FaClock className="mr-2" />
                        08:00 - 09:00
                    </p>
                    {/* Ngày khám */}
                    <p className="my-1 text-[#ff9900] flex items-center">
                        <FaCalendarAlt className="mr-2" />
                        Monday - 14/1/2025
                    </p>
                    <p>Phòng khám Chuyên khoa Da liễu</p>
                    <p>Phường 12, Quận 10, Thành phố Hồ Chí Minh</p>
                </div>
            </div>
        </div>
    );
}
