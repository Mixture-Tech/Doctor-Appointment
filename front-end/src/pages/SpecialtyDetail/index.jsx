import React, { useState } from 'react';
import Header from './components/Header';
import DiseaseList from './components/DiseaseList';
import DoctorCard from './components/DoctorCard';

export default function BoneJointPage() {
    const [selectedDate, setSelectedDate] = useState('');
    const doctors = [
        {
            name: "Phó Giáo sư, Tiến sĩ Đinh Ngọc Sơn",
            avatar: "https://cdn.bookingcare.vn/fo/w256/2018/06/07/112614pho-giao-su-tien-si-dinh-ngoc-son.jpg", // Add avatar
            experience: "25 năm kinh nghiệm về bệnh lý liên quan cột sống",
            position: "Trưởng khoa Phẫu thuật Cột sống, Bệnh viện Việt Đức",
            ageRange: "7 tuổi trở lên",
            location: "Hà Nội",
            clinic: "Phòng khám Spinetech Clinic",
            address: "Tòa nhà GP, 257 Giải Phóng, Phương Mai, Đống Đa, Hà Nội",
            price: "500.000đ",
            schedule: [
                "07:00 - 07:30",
                "07:30 - 08:00",
                "08:00 - 08:30",
                "08:30 - 09:00",
                "09:00 - 09:30",
                "09:30 - 10:00",
                "10:00 - 10:30",
                "10:30 - 11:00",
                "11:00 - 11:30",
                "11:30 - 12:00",
            ],
        },
        {
            name: "ThS.BS Nguyễn Trần Trung",
            avatar: "https://cdn.bookingcare.vn/fo/w256/2024/08/29/114134-bs-nguyen-tran-trung1.jpg", // Add avatar
            experience: "Nhiều năm kinh nghiệm trong khám và điều trị Cơ xương khớp",
            position: "Phó trưởng khoa Cơ Xương Khớp Bệnh viện E",
            ageRange: "15 tuổi trở lên",
            location: "Hà Nội",
            clinic: "Phòng Khám Đa Khoa MSC Clinic",
            address: "TT 20-21-22 Số 204 Nguyễn Tuân, phường Nhân Chính, quận Thanh Xuân, TP Hà Nội",
            price: "500.000đ",
            schedule: [
                "07:00 - 07:30",
                "07:30 - 08:00",
                "08:00 - 08:30",
                "08:30 - 09:00",
                "09:00 - 09:30",
                "09:30 - 10:00",
                "10:00 - 10:30",
                "10:30 - 11:00",
            ],
        },
    ];

    const availableDates = [
        "2024-11-27",
        "2024-11-28",
        "2024-11-29",
        "2024-11-30",
        "2024-12-01",
    ];

    return (
        <div className="bone-joint-page container mx-auto px-4 py-8 md:px-28">
            <Header />
            <DiseaseList />
            <section>
                <h2 className="text-2xl font-semibold mb-6">Danh sách bác sĩ</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {doctors.map((doctor, index) => (
                        <DoctorCard
                            key={index}
                            doctor={doctor}
                            availableDates={availableDates}
                            selectedDate={selectedDate}
                            setSelectedDate={setSelectedDate}
                        />
                    ))}
                </div>
            </section>
        </div>
    );
}
