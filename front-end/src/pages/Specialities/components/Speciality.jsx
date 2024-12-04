import React from 'react';
import SpecialtyCard from './SpecialtyCard';
import PropTypes from 'prop-types';

export default function Speciality({ item }) {
    return (
        <div className="container mx-auto p-4">
            <h1 className="text-xl font-bold mb-4 md:text-2xl">Danh sách chuyên khoa</h1>
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                {item.map((specialty) => (
                    <SpecialtyCard
                        key={specialty.specialization_id}
                        name={specialty.specialization_name}
                        imageUrl={`/assets/images/specializations/${specialty.specialization_image}`}
                    />
                ))}
            </div>
        </div>
    );
}

Speciality.propTypes = {
    item: PropTypes.arrayOf(
        PropTypes.shape({
            specialization_id: PropTypes.string.isRequired,
            specialization_name: PropTypes.string.isRequired,
            specialization_image: PropTypes.string.isRequired
        })
    ).isRequired
};