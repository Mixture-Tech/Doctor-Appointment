import React from 'react';
import DoctorSchedule from './DoctorSchedule';

export default function DoctorCard({ doctor, availableDates, selectedDate, setSelectedDate }) {
    return (
        <div className="border border-gray-200 shadow-sm rounded-lg p-6 bg-white md:p-4">
            <div className="mb-4 flex flex-col md:flex-row items-center gap-4">
                <img
                    src={doctor.avatar}
                    alt={doctor.name}
                    className="w-16 h-16 rounded-full object-cover md:w-12 md:h-12"
                />
                <div>
                    <h3 className="text-xl font-semibold text-primary-600">{doctor.name}</h3>
                    <p className="text-gray-700">{doctor.experience}</p>
                    <p className="text-gray-700">{doctor.position}</p>
                    <p className="text-gray-700">Bác sĩ nhận khám từ {doctor.ageRange}</p>
                    <p className="text-gray-700">Khu vực: {doctor.location}</p>
                </div>
            </div>
            <DoctorSchedule
                doctor={doctor}
                availableDates={availableDates}
                selectedDate={selectedDate}
                setSelectedDate={setSelectedDate}
            />
        </div>
    );
}
