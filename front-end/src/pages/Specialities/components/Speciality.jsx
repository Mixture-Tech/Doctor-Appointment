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
                        key={specialty.id}
                        name={specialty.name}
                        imageUrl={specialty.imageUrl}
                    />
                ))}
            </div>
        </div>
    );
}

Speciality.propTypes = {
    item: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            imageUrl: PropTypes.string.isRequired
        })
    ).isRequired
};
