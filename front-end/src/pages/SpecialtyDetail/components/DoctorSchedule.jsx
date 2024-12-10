import React from 'react';
import TimeButton from '../../../components/Form/Appointment/TimeButton';

export default function DoctorSchedule({ doctor, availableDates, selectedDate, setSelectedDate }) {
    return (
        <div>
            <h4 className="text-center md:text-left font-semibold mb-2">Lịch khám</h4>
            <div className="mb-4 text-center md:text-left">
                <label htmlFor={`date-select-${doctor.name}`} className="block mb-2 text-gray-700">
                    Chọn ngày khám:
                </label>
                <select
                    id={`date-select-${doctor.name}`}
                    value={selectedDate}
                    onChange={(e) => setSelectedDate(e.target.value)}
                    className="border border-gray-300 rounded px-4 py-2 w-auto"
                >
                    <option value="">27/11/2024</option>
                    {availableDates.map((date, i) => (
                        <option key={i} value={date}>
                            {new Date(date).toLocaleDateString('vi-VN')}
                        </option>
                    ))}
                </select>
            </div>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-4 mb-2">
                {doctor.schedule.map((time, i) => {
                    const slot = { time, status: 'available' };
                    return (
                        <TimeButton
                            key={i}
                            onClick={() => {
                                if (slot.status === 'available' && selectedDate) {
                                    console.log(`Đặt lịch vào ${selectedDate} lúc ${slot.time}`);
                                }
                            }}
                            className={`flex justify-center items-center w-26 h-12 rounded md:w-20 md:h-8 ${
                                slot.status === 'available' && selectedDate
                                    ? 'bg-primary-500 text-white hover:bg-primary-600'
                                    : 'bg-gray-200 text-gray-500 cursor-not-allowed'
                            }`}
                        >
                            <span>{slot.time}</span>
                        </TimeButton>
                    );
                })}
            </div>
        </div>
    );
}
