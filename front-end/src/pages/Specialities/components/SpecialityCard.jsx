import React from 'react';
import PropTypes from 'prop-types';

export default function SpecialtyCard({ name, imageUrl }) {
    return (
        <div className="flex flex-col items-center p-4 border rounded-lg shadow-sm hover:shadow-md transition-shadow">
            <img
                src={imageUrl}
                alt={name}
                className="w-28 h-28 object-cover rounded"
            />
            <span className="mt-2 text-lg font-medium text-center">{name}</span>
        </div>
    );
}

SpecialtyCard.propTypes = {
    name: PropTypes.string.isRequired,
    imageUrl: PropTypes.string.isRequired
};
