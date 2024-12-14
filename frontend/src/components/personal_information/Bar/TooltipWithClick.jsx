import React, { useEffect, useRef } from "react";
import bootstrap from "bootstrap/dist/js/bootstrap.bundle.min";
import PropTypes from "prop-types";

const TooltipWithClickAndHover = ({ emailCompany }) => {
    const tooltipTriggerRef = useRef(null);
    const isClickedRef = useRef(false);
    const isHoverRef = useRef(false);

    useEffect(() => {
        const tooltipTrigger = tooltipTriggerRef.current;
        const tooltip = new bootstrap.Tooltip(tooltipTrigger, {
            trigger: 'manual', // Manual control
        });

        const handleMouseEnter = () => {
            if (!isClickedRef.current) {
                tooltip.show(); // Show tooltip on hover
                isHoverRef.current = true;
            }
        };

        const handleMouseLeave = () => {
            if (!isClickedRef.current) {
                tooltip.hide(); // Hide tooltip on mouse leave
                isHoverRef.current = false;
            }
        };

        const handleClick = (e) => {
            if (tooltipTrigger.contains(e.target)) {
                // Toggle tooltip on click
                if (tooltipTrigger.getAttribute("aria-describedby") && isHoverRef.current === false) {
                    tooltip.hide();
                    isClickedRef.current = false;
                } else {
                    if (tooltipTrigger.getAttribute("aria-describedby") && isHoverRef.current === true) {
                        isClickedRef.current = true;
                        isHoverRef.current = false;
                    } else {
                        tooltip.show();
                        isClickedRef.current = true;
                        isHoverRef.current = false;
                    }
                }
            } else {
                tooltip.hide(); // Hide tooltip on outside click
                isClickedRef.current = false;
            }
        };

        tooltipTrigger.addEventListener("mouseenter", handleMouseEnter);
        tooltipTrigger.addEventListener("mouseleave", handleMouseLeave);
        document.addEventListener("click", handleClick);

        return () => {
            // Clean up event listeners
            tooltipTrigger.removeEventListener("mouseenter", handleMouseEnter);
            tooltipTrigger.removeEventListener("mouseleave", handleMouseLeave);
            document.removeEventListener("click", handleClick);
        };
    }, []);

    return (
        <button
            ref={tooltipTriggerRef}
            className="icon-button me-2"
            data-bs-toggle="tooltip"
            data-bs-placement="top"
            title={emailCompany || "N/A"}
        >
            <i className="fas fa-envelope"></i>
        </button>
    );
};

TooltipWithClickAndHover.propTypes = {
    emailCompany: PropTypes.string,
};

export default TooltipWithClickAndHover;