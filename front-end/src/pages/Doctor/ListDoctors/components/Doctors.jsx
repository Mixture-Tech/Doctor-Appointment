import React from "react";
import DoctorCard from "../components/DoctorCard";
import PropTypes from "prop-types";

export default function Doctors({ item, onDoctorClick }) {
    return (
        <div className="container mx-auto p-4">
            <h1 className="text-xl font-bold md:text-2xl">Danh sách bác sĩ</h1>
            <div className="h-1 bg-primary mb-8" style={{ width: "210px" }}></div>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                {item.map((doctor) => (
                    <DoctorCard
                        key={doctor.id}
                        name={doctor.name}
                        imageUrl={doctor.imageUrl}
                        specialty={doctor.specialty}
                        onClick={() => onDoctorClick(doctor.id)}
                    />
                ))}
            </div>
        </div>
    );
}

Doctors.propTypes = {
    item: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.string.isRequired,
            name: PropTypes.string.isRequired,
            imageUrl: PropTypes.string.isRequired,
            specialty: PropTypes.string.isRequired,
        })
    ).isRequired,
    onDoctorClick: PropTypes.func.isRequired,
};
