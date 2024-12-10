// BoneJointPage Component
import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import DiseaseList from './components/DiseaseList';
import DoctorCard from './components/DoctorCard';
import axiosClient from '../../services/apis/axiosClient';
import { useParams } from 'react-router-dom';

export default function BoneJointPage() {
    const { id } = useParams();
    const [doctors, setDoctors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedDates, setSelectedDates] = useState({}); // Lưu trữ ngày làm việc của từng bác sĩ
    const [availableDates, setAvailableDates] = useState([]);

    useEffect(() => {
        const fetchDoctorsBySpecialization = async () => {
            try {
                const response = await axiosClient.get(`/specializations/${id}`);
                if (response.data) {
                    const dates = response.data.flatMap((doctor) =>
                        doctor.schedules.map((schedule) => schedule.working_date)
                    );
                    const uniqueDates = [...new Set(dates)];
                    setAvailableDates(uniqueDates);
                    setDoctors(response.data);
                }
            } catch (err) {
                console.error('Error fetching doctors:', err);
                setError('Có lỗi xảy ra khi tải dữ liệu bác sĩ');
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchDoctorsBySpecialization();
        }
    }, [id]);

    const handleDateChange = (doctorId, date) => {
        console.log("doctorId:", doctorId, "date:", date); // Log values to verify
        if (doctorId && date) {
            setSelectedDates((prevDates) => ({
                ...prevDates,
                [doctorId]: date,
            }));
        } else {
            console.error("Thông tin bác sĩ hoặc ngày không hợp lệ");
        }
    };

    useEffect(() => {
        console.log('Selected Dates:', selectedDates);
    }, [selectedDates]); // Kiểm tra sự thay đổi trong selectedDates

    if (loading) return <div>Đang tải danh sách bác sĩ...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="bone-joint-page container mx-auto px-4 py-8">
            <Header />
            <DiseaseList />
            <section>
                <h2 className="text-2xl font-semibold mb-6">Danh sách bác sĩ</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {doctors.map((doctor) => (
                        <DoctorCard
                            key={doctor.doctor_id}
                            doctor={doctor}
                            availableDates={availableDates}
                            selectedDate={selectedDates[doctor.doctor_id] || ''}
                            setSelectedDate={(date) => {
                                if (doctor.doctor_id) {
                                    console.log(`Chọn ngày cho bác sĩ ${doctor.doctor_name}:`, date);
                                    handleDateChange(doctor.doctor_id, date);
                                } else {
                                    console.error("Không tìm thấy doctorId hợp lệ");
                                }
                            }}
                        />

                    ))}
                </div>
            </section>
        </div>
    );
}
