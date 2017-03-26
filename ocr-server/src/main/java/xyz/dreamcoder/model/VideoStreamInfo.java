package xyz.dreamcoder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoStreamInfo {

    // common properties
    private Integer index;
    private String codec_name;
    private String codec_long_name;
    private String profile;
    private String codec_type;
    private String codec_time_base;
    private String codec_tag_string;
    private String codec_tag;
    private String sample_fmt;
    private String sample_rate;
    private Integer bits_per_sample;
    private String r_frame_rate;
    private String avg_frame_rate;
    private String time_base;
    private Integer start_pts;
    private String start_time;
    private Integer duration_ts;
    private String duration;
    private String bit_rate;
    private String max_bit_rate;
    private String nb_frames;

    // video properties
    private Integer width;
    private Integer height;
    private Integer coded_width;
    private Integer coded_height;
    private Integer has_b_frames;
    private String sample_aspect_ratio;
    private String display_aspect_ratio;
    private String pix_fmt;
    private Integer level;
    private String chroma_location;
    private Integer refs;
    private String is_avc;
    private String nal_length_size;
    private String bits_per_raw_sample;

    // audio properties
    private Integer channels;
    private String channel_layout;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCodec_name() {
        return codec_name;
    }

    public void setCodec_name(String codec_name) {
        this.codec_name = codec_name;
    }

    public String getCodec_long_name() {
        return codec_long_name;
    }

    public void setCodec_long_name(String codec_long_name) {
        this.codec_long_name = codec_long_name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCodec_type() {
        return codec_type;
    }

    public void setCodec_type(String codec_type) {
        this.codec_type = codec_type;
    }

    public String getCodec_time_base() {
        return codec_time_base;
    }

    public void setCodec_time_base(String codec_time_base) {
        this.codec_time_base = codec_time_base;
    }

    public String getCodec_tag_string() {
        return codec_tag_string;
    }

    public void setCodec_tag_string(String codec_tag_string) {
        this.codec_tag_string = codec_tag_string;
    }

    public String getCodec_tag() {
        return codec_tag;
    }

    public void setCodec_tag(String codec_tag) {
        this.codec_tag = codec_tag;
    }

    public String getSample_fmt() {
        return sample_fmt;
    }

    public void setSample_fmt(String sample_fmt) {
        this.sample_fmt = sample_fmt;
    }

    public String getSample_rate() {
        return sample_rate;
    }

    public void setSample_rate(String sample_rate) {
        this.sample_rate = sample_rate;
    }

    public Integer getBits_per_sample() {
        return bits_per_sample;
    }

    public void setBits_per_sample(Integer bits_per_sample) {
        this.bits_per_sample = bits_per_sample;
    }

    public String getR_frame_rate() {
        return r_frame_rate;
    }

    public void setR_frame_rate(String r_frame_rate) {
        this.r_frame_rate = r_frame_rate;
    }

    public String getAvg_frame_rate() {
        return avg_frame_rate;
    }

    public void setAvg_frame_rate(String avg_frame_rate) {
        this.avg_frame_rate = avg_frame_rate;
    }

    public String getTime_base() {
        return time_base;
    }

    public void setTime_base(String time_base) {
        this.time_base = time_base;
    }

    public Integer getStart_pts() {
        return start_pts;
    }

    public void setStart_pts(Integer start_pts) {
        this.start_pts = start_pts;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Integer getDuration_ts() {
        return duration_ts;
    }

    public void setDuration_ts(Integer duration_ts) {
        this.duration_ts = duration_ts;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBit_rate() {
        return bit_rate;
    }

    public void setBit_rate(String bit_rate) {
        this.bit_rate = bit_rate;
    }

    public String getMax_bit_rate() {
        return max_bit_rate;
    }

    public void setMax_bit_rate(String max_bit_rate) {
        this.max_bit_rate = max_bit_rate;
    }

    public String getNb_frames() {
        return nb_frames;
    }

    public void setNb_frames(String nb_frames) {
        this.nb_frames = nb_frames;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getCoded_width() {
        return coded_width;
    }

    public void setCoded_width(Integer coded_width) {
        this.coded_width = coded_width;
    }

    public Integer getCoded_height() {
        return coded_height;
    }

    public void setCoded_height(Integer coded_height) {
        this.coded_height = coded_height;
    }

    public Integer getHas_b_frames() {
        return has_b_frames;
    }

    public void setHas_b_frames(Integer has_b_frames) {
        this.has_b_frames = has_b_frames;
    }

    public String getSample_aspect_ratio() {
        return sample_aspect_ratio;
    }

    public void setSample_aspect_ratio(String sample_aspect_ratio) {
        this.sample_aspect_ratio = sample_aspect_ratio;
    }

    public String getDisplay_aspect_ratio() {
        return display_aspect_ratio;
    }

    public void setDisplay_aspect_ratio(String display_aspect_ratio) {
        this.display_aspect_ratio = display_aspect_ratio;
    }

    public String getPix_fmt() {
        return pix_fmt;
    }

    public void setPix_fmt(String pix_fmt) {
        this.pix_fmt = pix_fmt;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getChroma_location() {
        return chroma_location;
    }

    public void setChroma_location(String chroma_location) {
        this.chroma_location = chroma_location;
    }

    public Integer getRefs() {
        return refs;
    }

    public void setRefs(Integer refs) {
        this.refs = refs;
    }

    public String getIs_avc() {
        return is_avc;
    }

    public void setIs_avc(String is_avc) {
        this.is_avc = is_avc;
    }

    public String getNal_length_size() {
        return nal_length_size;
    }

    public void setNal_length_size(String nal_length_size) {
        this.nal_length_size = nal_length_size;
    }

    public String getBits_per_raw_sample() {
        return bits_per_raw_sample;
    }

    public void setBits_per_raw_sample(String bits_per_raw_sample) {
        this.bits_per_raw_sample = bits_per_raw_sample;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public String getChannel_layout() {
        return channel_layout;
    }

    public void setChannel_layout(String channel_layout) {
        this.channel_layout = channel_layout;
    }
}